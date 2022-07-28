package freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Freemarker {

    public static final String XML_TEMPLATES_FOLDER = "xml_templates";

    private Map<String, Object> dataModel = new HashMap<>();
    private File tempDirectory;
    private String templateName;

    public Freemarker withTemplate(String template) {
        this.templateName = template + ".ftlh";
        return this;
    }

    public Freemarker tmpDir(File temp) {
        tempDirectory = temp;
        return this;
    }

    public Freemarker onData(Map<String, Object> newModel) {
        dataModel = newModel;
        return this;
    }

    public Freemarker addParam(String key, Object value) {
        dataModel.put(key, value);
        return this;
    }

    public File create(String template) throws IOException {
        Template temp = configuration().getTemplate(templateName);
        File output = new File(tempDirectory, template + ".xml");
        System.out.println(output.getAbsolutePath());
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8 );
            temp.process(dataModel, out);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't process freemarker template");
        }

    }

    public Freemarker print() throws IOException {
        Template temp = configuration().getTemplate(templateName);
        try {
            Writer text = new StringWriter();
            temp.process(dataModel, text);
            System.out.println(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public Configuration configuration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateLoader(new ClassTemplateLoader(Freemarker.class.getClassLoader(), XML_TEMPLATES_FOLDER));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        return cfg;
    }
}
