package org.tonguetied.audit;

import junitx.extensions.EqualsHashCodeTestCase;

import org.tonguetied.keywordmanagement.Keyword;


/**
 * @author bsion
 *
 */
public class AuditLogRecordEqualsHashCodeTest extends EqualsHashCodeTestCase {

    public AuditLogRecordEqualsHashCodeTest(String name) {
        super(name);
    }

    @Override
    protected Object createInstance() throws Exception {
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        
        AuditLogRecord record = new AuditLogRecord("new", keyword, "user");

        return record;
    }

    @Override
    protected Object createNotEqualInstance() throws Exception {
        Keyword keyword = new Keyword();
        keyword.setKeyword("test");
        
        AuditLogRecord record = new AuditLogRecord("delete", keyword, "user");

        return record;
    }
}
