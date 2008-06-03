INSERT INTO LANGUAGE(code, name) VALUES ('DEFAULT', 'Default(English)');
INSERT INTO COUNTRY(code, name) VALUES ('DEFAULT', 'Default');
INSERT INTO BUNDLE(name, resourcename, description, isdefault, isglobal) 
    VALUES ('All', 'all', 'This bundle means the keyword applies to all bundles', true, true);
INSERT INTO USER(username, password, firstname, lastname, enabled, email, accountNonExpired, accountNonLocked, credentialsNonExpired) 
    VALUES('admin', 'a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1', 'admin', 'admin', 1, 'none', 1, 1, 1);
INSERT INTO authorities(userid, permission) VALUES(1, 'ROLE_ADMIN');
