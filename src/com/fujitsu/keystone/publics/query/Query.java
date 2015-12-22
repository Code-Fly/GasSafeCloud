/**
 *
 */
package com.fujitsu.keystone.publics.query;

import com.fujitsu.keystone.publics.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Barrie
 */
public abstract class Query extends Event {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String FILLING_STORAGE_DETAIL = "1";

    public static final String FILLING_STORAGE_LIST = "11";

    public static final String DISTRIBUTION_TRANSPORTATION_DETAIL = "2";

    public static final String DISTRIBUTION_TRANSPORTATION_LIST = "22";

    public static final String INSPECTION_TESTING_DETAIL = "3";

    public static final String INSPECTION_TESTING_LIST = "33";

    public static final Map<String, String> QUERY_CMD_TYPE = new HashMap<String, String>() {
        {
            put(FILLING_STORAGE_DETAIL, "A");

            put(FILLING_STORAGE_LIST, "A");

            put(DISTRIBUTION_TRANSPORTATION_DETAIL, "B");

            put(DISTRIBUTION_TRANSPORTATION_LIST, "B");

            put(INSPECTION_TESTING_DETAIL, "C");

            put(INSPECTION_TESTING_LIST, "C");
        }
    };

    public static final String SEPARATOR_1 = "+";

    public static final String SEPARATOR_2 = "#";

    public static final String SEPARATOR_3 = "-";

}
