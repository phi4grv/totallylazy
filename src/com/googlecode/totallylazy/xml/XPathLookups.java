package com.googlecode.totallylazy.xml;

import com.googlecode.totallylazy.Function;
import com.googlecode.totallylazy.Function;
import com.googlecode.totallylazy.Xml;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XPathLookups {
    private static final Map<String, Function<String, String>> lookups = new ConcurrentHashMap<String, Function<String, String>>();

    @XPathFunction("lookup")
    public static NodeArrayList lookup(String name, NodeList types) {
        return new NodeArrayList<Text>(Xml.sequence(types).map(lookup(lookups.get(name))));
    }

    private static Function<Node, Text> lookup(final Function<String, String> data) {
        return new Function<Node, Text>() {
            @Override
            public Text call(Node node) throws Exception {
                return XPathFunctions.createText(node, data.call(node.getTextContent()));
            }
        };
    }

    public static void setLookup(String name, Function<String, String> lookup){
        lookups.put(name, lookup);
    }

}