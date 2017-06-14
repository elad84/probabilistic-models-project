package com.idc.computersience.pm.model.reader;

import com.idc.computersience.pm.model.Node;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author eladcohen
 */
@Ignore
public class YamlReaderTest {

    private YamlNodeReader yamlNodeReader = new YamlNodeReader();

    @Test
    public void testValidFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("nodes.yaml").getFile());
        List<Node> nodes = yamlNodeReader.readNodes(file.getAbsolutePath());
        Assert.assertTrue("expected to have two nodes but got: " + nodes, nodes != null && nodes.size() == 2);
    }
}
