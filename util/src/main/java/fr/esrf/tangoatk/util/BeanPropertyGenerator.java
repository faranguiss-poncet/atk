/*
 *  Copyright (C) :	2002,2003,2004,2005,2006,2007,2008,2009
 *			European Synchrotron Radiation Facility
 *			BP 220, Grenoble 38043
 *			FRANCE
 * 
 *  This file is part of Tango.
 * 
 *  Tango is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Tango is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */

// File:          BeanPropertyGenerator.java
// Created:       2002-01-25 13:20:42, assum
// By:            <assum@esrf.fr>
// Time-stamp:    <2002-04-25 13:39:44, assum>
//
// $Id$
//
// Description:


package fr.esrf.tangoatk.util;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.IllegalFormatException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class BeanPropertyGenerator extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}/src/main/resources")
    private File imagesRoot;
    @Parameter(defaultValue = "${project}", readonly = true)
    MavenProject project;


    public static final String PROJECT = "project";
    public static final String TARGET_GENERATED_SOURCES = "/target/generated-sources/beaninfo/";
    int indentLevel = 4;
    PrintStream out;
    String name;
    Properties prop;
    String _package;
    String _packagePath;
    String color16;
    String color32;

    public BeanPropertyGenerator() {
    }

    public void execute(InputStream properties, File baseDir, String javaName)
            throws java.io.IOException {

        int level = 0;


        prop = new Properties();
        prop.load(properties);
        name = prop.getProperty("name").trim();
        _package = prop.getProperty("package").trim();
        _packagePath = _package.replace('.', '/');


        File packageDir = new File(baseDir.getAbsolutePath() + "/" + _packagePath);
        FileUtils.forceMkdir(packageDir);

        this.out = new PrintStream(new FileOutputStream(FileUtils.getFile(packageDir, javaName)));

        color16 = prop.getProperty("ICON_COLOR_16x16");
        color32 = prop.getProperty("ICON_COLOR_32x32");

        generateBeanHeader();


        if (color16 != null) {
            File icon16File = FileUtils.getFile(
                    FileUtils.getFile(imagesRoot,_packagePath.split("/")),color16);
            generateIconArray(icon16File, 16, 16);
        }
        if (color32 != null) {
            File icon32File = FileUtils.getFile(
                    FileUtils.getFile(imagesRoot,_packagePath.split("/")),color32);
            generateIconArray(icon32File, 32, 32);
        }

        generateConstructor();

        level++;
        if (!"".equals(prop.getProperty("events", "").trim()))
            generateEventSetDescriptor(level, new StringTokenizer(prop.getProperty("events").trim(), ","));

        if (!"".equals(prop.getProperty("properties", "").trim()))
            generatePropertyDescriptor(level, new StringTokenizer(prop.getProperty("properties").trim(), ","));

        generateIcon(level);
        generateAdditionalBeanInfo(level);
        generateBeanFooter(--level);


    }

    /**
     * Get the value of name.
     *
     * @return value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name.
     *
     * @param v Value to assign to name.
     */
    public void setName(String v) {
        this.name = v;
    }


    /**
     * Get the value of indent.
     *
     * @return value of indent.
     */
    public int getIndentLevel() {
        return indentLevel;
    }

    /**
     * Set the value of indent.
     *
     * @param v Value to assign to indent.
     */
    public void setIndentLevel(int v) {
        this.indentLevel = v;
    }

    int generateBeanHeader() {

        String className = name + "BeanInfo";

        out.println("// This class is autogenerated by the BeanPropertyGenerator");
        out.println("// Do not edit");
        out.println("// See " + className + ".info");
        out.println("");
        out.println("package " + _package + ";");
        out.println();
        out.println("import java.beans.*;");
        out.println("import java.awt.image.BufferedImage;");
        out.println("");
        out.println("public class " + className + " extends SimpleBeanInfo {");
        out.println("");
        out.println("  private BufferedImage icon16x16 = null;");
        out.println("  private BufferedImage icon32x32 = null;");
        out.println("");

        return 1;

    }

    int generateConstructor() {

        String className = name + "BeanInfo";

        out.println("    public " + className + "() {");
        out.println("");

        if (color16 != null) {
            out.println("      icon16x16 = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);");
            out.println("      icon16x16.setRGB(0,0,16,16,icon16x16_data,0,16);");
        }

        if (color32 != null) {
            out.println("      icon32x32 = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);");
            out.println("      icon32x32.setRGB(0,0,32,32,icon32x32_data,0,32);");
        }
        out.println("");

        out.println("    }");

        return 1;
    }


    int generateIconArray(String iconName, int dimX, int dimY) throws IOException {
        return generateIconArray(iconName, dimX, dimY);
    }

    int generateIconArray(File icon, int dimX, int dimY) throws IOException {

        BufferedImage img;

        try {
            img = ImageIO.read(icon);
        } catch (IOException ex) {
            System.err.println("Failed to read " + icon);
            throw ex;
        }

        if (img.getWidth() != dimX) throw new IOException(icon + ":Invalid width dimension (must be " + dimX + ")");
        if (img.getHeight() != dimY)
            throw new IOException(icon + ":Invalid height dimension (must be " + dimY + ")");

        int[] rgbArray;
        int nbData = dimX * dimY;

        rgbArray = img.getRGB(0, 0, dimX, dimY, null, 0, dimX);

        String dataName = "icon" + dimX + "x" + dimY + "_data";

        out.print("  private int[] " + dataName + " = new int[]{");

        for (int i = 0; i < nbData; i++) {

            if (i % 8 == 0) {
                out.println("");
                out.print("    ");
            }
            out.print("0x" + format("%X", rgbArray[i]));
            if (i < nbData - 1) out.print(",");

        }
        out.println("};");
        out.println("");

        return 1;

    }

    int generateBeanFooter(int level) {

        out.print(generatePad(level));
        out.println("}");

        return 0;
    }

    String generatePad(int level) {
        StringBuffer pad = new StringBuffer();
        int length = level * indentLevel;

        for (; length >= 0; length--) {
            pad.append(" ");
        }
        return pad.toString();
    }

    void generateHeader(String retval, String name, int level, boolean tc) {
        StringBuffer line =
                new StringBuffer(generatePad(level));
        line.append("public ");
        line.append(retval);
        line.append("[] get");
        line.append(name);
        line.append("() {");
        out.println(line);
        out.println("");
        if (tc) {
            level++;
            line.delete(0, line.length());

            line.append(generatePad(level));
            line.append("try {");
            out.println(line);
        }


    }

    void generateHeader(String name, int level) {
        generateHeader(name, name + "s", level, true);
    }

    void generateHeader(String name, int level, boolean tc) {
        generateHeader(name, name + "s", level, tc);
    }

    void generateFooter(String name, int level) {

        level--;
        StringBuffer line = new StringBuffer(generatePad(level));
        line.append("} catch (Exception e) {");
        out.println(line);
        line.delete(0, line.length());
        level++;
        line.append(generatePad(level));
        line.append("System.err.println(\"" + prop.getProperty("name").trim()
                + "\" + \":\");\n");
        line.append(generatePad(level));
        line.append("System.err.println(e);");

        out.println(line);
        level--;
        line.delete(0, line.length());
        line.append(generatePad(level));
        line.append("}");
        out.println(line);

        line.delete(0, line.length());
        line.append(generatePad(level));
        line.append("return null;");
        out.println(line);
        line.delete(0, line.length());
        level--;
        line.append(generatePad(level));
        line.append("}");
        out.println(line);
        out.println();
        out.println();

    }

    void generateTry(int level) {

        printPad(level, "try {");

    }

    void generateCatch(int level, String exception) {

        out.print(generatePad(--level));
        out.println("} catch (" + exception + ") {");

    }

    void printPad(int level, String s) {

        out.print(generatePad(level));
        out.println(s);

    }

    void generateAdditionalBeanInfo(int level) {

        StringBuffer retval =
                new StringBuffer("return new BeanInfo[] { sbi };");
        generateHeader("BeanInfo", "AdditionalBeanInfo", level, true);
        level++;
        printPad(++level, "Class s = " +
                getName() +
                ".class.getSuperclass();");
        printPad(++level, "BeanInfo sbi = Introspector.getBeanInfo(s);");
        printPad(level, retval.toString());
        level--;
        generateCatch(level, "IntrospectionException e");
        printPad(level, "System.out.println(e);");
        printPad(level, "return null;");
        printPad(--level, "}");
        printPad(--level, "}");
        out.println();

    }


    void generatePropertyDescriptor(int level, StringTokenizer properties) {

        String property;
        generateHeader("PropertyDescriptor", level, false);
        level++;
        List plist = new Vector();

        while (properties.hasMoreTokens()) {
            plist.add(properties.nextToken().trim());
        }
        printPad(level, "PropertyDescriptor [] propdesc = new PropertyDescriptor[" +
                plist.size() + "];");

        for (int i = 0; i < plist.size(); i++) {
            generateTry(level++);
            property = (String) plist.get(i);
            printPad(level, "propdesc[" + i +
                    "] = new PropertyDescriptor(\"" + property + "\", " +
                    name + ".class);");
            generateCatch(level, "Exception e");
            printPad(level, "System.out.println(\"\\n" + property +
                    " not supported (please verify your code)\");");
            printPad(--level, "}");
        }
        printPad(level, "return propdesc;");
        printPad(--level, "}");
        out.println();

    }


    void generateEventSetDescriptor(int level,
                                    StringTokenizer events) {

        generateHeader("EventSetDescriptor", level);
        level++;
        StringBuffer retval =
                new StringBuffer("return new EventSetDescriptor [] { ");
        String event;
        String listenerType;
        String listenerMethodName;
        String displayName;

        while (events.hasMoreTokens()) {
            out.print(generatePad(++level));
            event = events.nextToken().trim();
            retval.append(event);
            retval.append(", ");
            listenerType = prop.getProperty(event + ".listenerType").trim();
            listenerMethodName = prop.getProperty(event +
                    ".listenerMethodName")
                    .trim();
            displayName = prop.getProperty(event + ".displayName").trim();
            ;
            out.print("EventSetDescriptor ");
            out.print(event);
            out.print(" = new EventSetDescriptor(");
            out.print(name);
            out.print(".class, \"");
            out.print(event);
            out.print("\", ");

            out.print(listenerType);
            out.print(".class, \"");
            out.print(listenerMethodName);
            out.println("\");");
            out.print(event);
            out.print(".setDisplayName(\"");
            out.print(displayName);
            out.println("\");");

        } // end of for ()
        retval.append(" };");
        out.println(retval);
        generateFooter("EventSetDescriptor", --level);

    }

    void generateIcon(int level) {

        if (color16 == null &&
                color32 == null)
            return;

        out.print(generatePad(level));
        out.println("public java.awt.Image getIcon(int icon) {\n");
        ++level;
        if (color16 != null) generateSingleIcon("icon16x16", "ICON_COLOR_16x16", level);
        if (color32 != null) generateSingleIcon("icon32x32", "ICON_COLOR_32x32", level);
        out.print(generatePad(level));
        out.println("return null;");
        out.print(generatePad(--level));
        out.println("}");
        out.println();

    }

    void generateSingleIcon(String iconName, String type, int level) {

        out.print(generatePad(level));
        out.println("if (icon == BeanInfo." + type + ") {");
        out.print(generatePad(++level));
        out.println("return " + iconName + ";");
        out.print(generatePad(--level));
        out.println("}");

    }

    private String format(String f, int value) {

        try {
            return String.format(f, value);
        } catch (IllegalFormatException e) {
            System.out.println("BeanPropertyGenerator.java: Warning, " + e.getMessage());
            return Integer.toHexString(value);
        }

    }

    private static String makeInfoName(String javaName) throws IOException {
        if (!javaName.endsWith(".java"))
            throw new IOException("Invalid filename " + javaName);
        return javaName.replaceAll(".java$", ".info");
    }

    private static String makeJavaName(String infoName) throws IOException {
        if (!infoName.endsWith(".info"))
            throw new IOException("Invalid filename " + infoName);
        return infoName.replaceAll(".info$", ".java");
    }


    public static void main(String[] args) throws IOException {

        String infoName = "";

        // Do nothing if no Beaninfo specified
        if (args.length == 0) {
            System.exit(0);
        }

        try {

            for (String javaName : args) {
                infoName = makeInfoName(javaName);
                BeanPropertyGenerator generator = new BeanPropertyGenerator();
                generator.execute(new FileInputStream(infoName),
                        new File(System.getProperty("user.dir")), javaName);
            }

        } catch (IOException e) {
            System.err.println("BeanPropertyGenerator failed : " + e.getMessage());
            System.err.println(" Hint: check " + System.getProperty("user.dir") + "/" + infoName);
            throw e;
        } // end of try-catch

    } // end of main ()

    public void execute() throws MojoExecutionException, MojoFailureException {
        //TODO log info
        File targetDir = new File(project.getBasedir().getAbsolutePath() + TARGET_GENERATED_SOURCES);

        try {
            FileUtils.forceMkdir(targetDir);
            Iterator<File> infos = FileUtils.iterateFiles(project.getBasedir(), new String[]{"info"}, true);
            while (infos.hasNext()) {
                File info = infos.next();
                String infoName = info.getName();
                String javaName = makeJavaName(infoName);

                execute(new FileInputStream(info), new File(project.getBasedir().getAbsolutePath() + TARGET_GENERATED_SOURCES), javaName);

            }
        } catch (IOException e) {
            getLog().error(e);
            throw new MojoExecutionException(e.getMessage());
        }

        project.addCompileSourceRoot(targetDir.getAbsolutePath());
    }
}

