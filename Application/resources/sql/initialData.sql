INSERT INTO LANGUAGE(code, name) VALUES ('DEFAULT', 'Default(English)');
INSERT INTO COUNTRY(code, name) VALUES ('DEFAULT', 'Default');
INSERT INTO BUNDLE(name, resourcename, description, isdefault, isglobal) VALUES ('All', 'All', 'This bundle means the keyword applies to all bundles', true, true);
INSERT INTO USER(username, password, firstname, lastname, enabled, email, accountNonExpired, accountNonLocked, credentialsNonExpired) 
	VALUES('admin', 'admin', 'admin', 'admin', 1, 'none', 1, 1, 1);
INSERT INTO authorities(userid, permission) VALUES(1, 'ROLE_ADMIN');