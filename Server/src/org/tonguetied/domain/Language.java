package org.tonguetied.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.AccessType;

/**
 * Object describing the features of a language. The language is one aspect 
 * used to determine a {@link Translation}.
 * 
 * @author mforslund
 */
@Entity
@AccessType("field")
@NamedQuery(name="get.languages",query="from Language l order by l.name")
public class Language implements Comparable<Object> 
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique=true,nullable=false,length=6)
    @Enumerated(EnumType.STRING)
    private LanguageCode code;
    @Column(nullable=false)
    private String name;
    
    public LanguageCode getCode() {
    	return code;
    }
    public void setCode(LanguageCode code) {
    	this.code = code;
    }
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
	
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object) {
        final Language other = (Language) object;
        return new CompareToBuilder().append(code, other.code).toComparison();
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        // a good optimization
        if (this == obj)
        {
            isEqual = true;
        }
        else if (obj instanceof Language)
        {
            final Language other = (Language)obj;
            
            isEqual = code.equals(other.code)
                    && name.equals(other.name);
        }
        
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        
        result = 31 * result + (this.code == null? 0: this.code.hashCode());
        result = 31 * result + (this.name == null? 0: this.name.hashCode());

        return result;
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }
    
    /**
     * This enum contains the list of 2 letter language codes according to 
     * iso639.2 arranged in alphabetical order of language names in English
     * 
     * @author bsion
     * @see http://www.loc.gov/standards/iso639-2/
     *
     */
    public enum LanguageCode {
        /**
         * Non ISO code representing the default language code
         */
        DEFAULT,
        aa,ab,ae,af,ak,am,an,ar,as,av,ay,az,ba,be,bg,bh,bi,bm,bn,bo,br,bs,ca,ce,
        ch,co,cr,cs,cu,cv,cy,da,de,dv,dz,ee,el,en,eo,es,et,eu,fa,ff,fi,fj,fo,fr,
        fy,ga,gd,gl,gn,gu,gv,ha,he,hi,ho,hr,ht,hu,hy,hz,ia,id,ie,ig,ii,ik,io,is,
        it,iu,ja,jv,ka,kg,ki,kj,kk,kl,km,kn,ko,kr,ks,ku,kv,kw,ky,la,lb,lg,li,ln,
        lo,lt,lu,lv,mg,mh,mi,mk,ml,mn,mo,mr,ms,mt,my,na,nb,nd,ne,ng,nl,nn,no,nr,
        nv,ny,oc,oj,om,or,os,pa,pi,pl,ps,pt,qu,rm,rn,ro,ru,rw,sa,sc,sd,se,sg,sh,
        sk,si,sm,sn,so,sq,sr,ss,st,su,sv,sw,ta,te,tg,th,ti,tk,tl,tn,to,tr,ts,
        tt,tw,ty,ug,uk,ur,uz,ve,vi,vo,wa,wo,xh,yi,yo,za,zh,zu,
        /**
         * Non ISO code representing traditional chinese
         */
        zht
    }
}
