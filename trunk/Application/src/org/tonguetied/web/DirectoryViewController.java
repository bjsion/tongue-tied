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
package org.tonguetied.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author bsion
 *
 */
public class DirectoryViewController extends AbstractController
{
    private String suffix;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response)
    {
        final StringBuilder servletPath = new StringBuilder(request.getServletPath());
        if (logger.isDebugEnabled())
            logger.debug("servletPath = " + servletPath);
        if (servletPath.toString().endsWith(suffix))
            servletPath.delete(servletPath.length()-4, servletPath.length());
        
        final String homePath = 
            request.getSession().getServletContext().getRealPath(servletPath.toString());
        if (logger.isDebugEnabled())
            logger.debug("homePath = " + homePath);
        
        FileBean baseDirectory = new FileBean(homePath);
        if (logger.isInfoEnabled())
            logger.info("displaying contents of basedir = " + baseDirectory);
//        final File homeDirectory = 
//            new File(request.getSession().getServletContext().getRealPath("/"));
//        List<String> parents = getParents(baseDirectory.getParentFile(), homeDirectory);
        int lastIndex = servletPath.lastIndexOf("/");
        final String[] parents;
        if (lastIndex > 0)
            parents = servletPath.substring(1, lastIndex).toString().split("/");
        else
            parents = new String[]{};
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("baseDir", baseDirectory);
        model.put("suffix", suffix);
        model.put("parents", parents);
        
        return new ModelAndView("export/fileListing", model);
    }

    private List<String> getParents(final File directory, final File homeDirectory)
    {
        List<String> parents = new Stack<String>();
        if (!directory.equals(homeDirectory))
        {
            List<String> grandParents = 
                getParents(directory.getParentFile(), homeDirectory);
            parents.addAll(grandParents);
            parents.add(directory.getName());
        }
        
        return parents;
    }
    
    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(final String suffix)
    {
        this.suffix = suffix;
    }
}
