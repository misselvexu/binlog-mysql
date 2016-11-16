package xyz.vopen.mysql.binlog.extension;

import xyz.vopen.mysql.binlog.DomainSerializable;

import java.util.Comparator;

/**
 * 关键字KEY
 */
@SuppressWarnings("rawtypes")
public class Key extends DomainSerializable implements Comparator {
    private static final long serialVersionUID = 6472828943790231601L;

    /**
     * key的小标索引
     */
    private Integer index;

    /**
     * key 的名称
     */
    private String key;

    @Override
    public int compare (Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            if (o1 instanceof Key && o2 instanceof Key) {
                Key _o1 = (Key) o1;
                Key _o2 = (Key) o2;
                if (_o1.getIndex() < _o2.getIndex()) {
                    return -1;
                } else if (_o1.getIndex() > _o2.getIndex()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }

    public Integer getIndex () {
        return index;
    }

    public void setIndex (Integer index) {
        this.index = index;
    }

    public Key () {
    }

    public Key (Integer index, String key) {
        this.index = index;
        this.key = key;
    }
}