/*
 * Copyright 2008 The Tongue-Tied Authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License. 
 */
package org.tonguetied.functionaltests

import com.canoo.webtest.WebtestCase;


/**
 * Functional tests to verify the user authorization and authentication of the
 * TongueTied application.
 * 
 * @author bsion
 *
 */
public class AuthenticationAndAuthorizationTest extends WebtestCase
{
    def config_map = [protocol: "http", host: "localhost", port: 8080, 
                      basepath: "tonguetied", browser: "Firefox3"];
    
    void testLogin()
    {
        webtest("Authenticate user with invalid credentials")
        {
            config(config_map);
            invokeAndValidateLoginPage("admin", "invalid", false, {
                verifyText(description: 
                    "Verify that the error message is contained in the page", 
                    "Failed to log in.");
                verifyText(description: 
                    "Verify that the reason error message is contained in the page",
                    "Reason: Bad credentials");
            });
        }
        
//TODO: why is &nbsp; evaluated differently to " "
        webtest("Authenticate admin user with valid credentials")
        {
            config(config_map);
            invokeAndValidateLoginPage("admin", "admin", false, {
                verifyDocumentURL(description: "Verify the resulting URL",
                        // TODO do not hard code the location, determine the 
                        // location based on the config settings
//                        step.context.config.urlForPage("keywords.htm"));
                        getUrl("keywords.htm"));
//                verifyLinks(description: "Verify all the links on this keyword page",
//                        onsiteonly: true);
                verifyText(description: 
                    "Verify that user greeting is contained in the page",
                    "hello&nbsp;admin");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "Keywords");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "New Keyword by Languages");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "New Keyword by Countries");
            });
        }

        webtest("Authenticate admin user using remember me feature")
        {
            config(config_map);
            invokeAndValidateLoginPage("admin", "admin", true, {
                verifyDocumentURL(description: "Verify the resulting URL",
                        getUrl("keywords.htm"));
//                verifyLinks(description: "Verify all the links on this keyword page",
//                        onsiteonly: true);
                verifyCookie(description: 
                    "Verify the cookie used to store the remember me feature",
                    name: "SPRING_SECURITY_REMEMBER_ME_COOKIE");
                verifyText(description: 
                    "Verify that user greeting is contained in the page",
                    "hello&nbsp;admin");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "Keywords");// * New Keyword by Languages * New Keyword by Countries");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "New Keyword by Languages");// * New Keyword by Countries");
                verifyText(description: 
                    "Verify that keyword sub menu is contained in the page", 
                    "New Keyword by Countries");
            });
        }
    }
    
    /**
     * Generica method to invoke the login page and run the login steps, then 
     * validate the response.
     * 
     * @param username the username to login with
     * @param password the password to login with
     * @param verification the steps to executed once the results page is 
     * reached
     */
    private invokeAndValidateLoginPage(String username, String password, boolean rememberMe, Closure verification)
    {
        ant.group(description: "call the login page and verify the contents")
        {
            steps
            {
                invoke(url: "login.jsp", description: "Go to the login page");
                selectWebClient(name: "default");
//                webClient.webConnection.state.clearCookies();
                verifyTitle "Tongue Tied";
                verifyText(description: "Verify that the user name label is contained in the page", "User name");
                verifyText(description: "Verify that the password label is contained in the page", "Password");
                verifyText(description: "Verify that the remember me label is contained in the page", "Remember me");
                setInputField(description: "Set password field j_username: " + username,
                        name: "j_username", value: username);
                setInputField(description: "Set password field j_password: " + password,
                        name: "j_password", value: password);
                setCheckbox(description: "Set the remember me field",
                        name: "_spring_security_remember_me", checked: rememberMe);
                clickButton "Login";
                verification();
            }
        }
    }
    
    /**
     * Construct the full URL location based on the config settings and the 
     * address component provided
     * 
     * @param address the address to append to the base url
     */
    private String getUrl(String address)
    {
        return config_map["protocol"]+"://"+
                config_map["host"]+":"+
                config_map["port"]+"/"+
                config_map["basepath"]+"/"+
                address;
    }
    
    /**
     * Validate a page against the W3C standard using the W3C website.
     */
    private void validateHtml()
    {
        steps()
        {
            invoke(description: "Get the page: somestuff.com", 
                    url: "http://www.somestuff.com");
            groovy(description:"Store the html of the page","""
              def document = step.context.currentResponse.asXml()
              def props = step.webtestProperties
              props.putAt("htmlPage",document)
               """);
            invoke(description:"goto W3C page",
                    url:"http://validator.w3.org/#validate_by_input");
            setInputField(description:'set textarea with feeds',
                    htmlId:'fragment',value="#{htmlPage}");
            clickButton(description:"Submit the simple feedvalidation",
                    xpath:"//fieldset[@id='validate-by-input']//input[@value='Check']");
        }
    }
}
