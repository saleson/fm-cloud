package com.fm.utils;

import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

public class WebUtils {

    /**
     *
     * @param urlQuery
     * @return
     */
    public static Map<String, List<String>> getQueryParams(String urlQuery) {
        Map<String, List<String>> qp = new HashMap<String, List<String>>();

        if (urlQuery == null) return qp;
        StringTokenizer st = new StringTokenizer(urlQuery, "&");
        int i;

        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            i = s.indexOf("=");
            if (i > 0 && s.length() >= i + 1) {
                String name = s.substring(0, i);
                String value = s.substring(i + 1);

                try {
                    name = URLDecoder.decode(name, "UTF-8");
                } catch (Exception e) {
                }
                try {
                    value = URLDecoder.decode(value, "UTF-8");
                } catch (Exception e) {
                }

                List<String> valueList = qp.get(name);
                if (valueList == null) {
                    valueList = new LinkedList<String>();
                    qp.put(name, valueList);
                }

                valueList.add(value);
            } else if (i == -1) {
                String name = s;
                String value = "";
                try {
                    name = URLDecoder.decode(name, "UTF-8");
                } catch (Exception e) {
                }

                List<String> valueList = qp.get(name);
                if (valueList == null) {
                    valueList = new LinkedList<String>();
                    qp.put(name, valueList);
                }

                valueList.add(value);

            }
        }
        return qp;
    }
}
