package com.openerp.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * This program demonstrates how to resize an image.
 *
 * @author www.codejava.net
 *
 */
public class ImageResizer {
 
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputStream Path of the original image
     * @param file Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(InputStream inputStream, File file,
                              int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        BufferedImage inputImage = ImageIO.read(inputStream);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, 1);
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // writes to output file
        ImageIO.write(outputImage, ".jpg", file);
    }

    public static byte[] resize(InputStream inputStream) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputStream);
        Double percent = 1d;
        if(inputImage.getWidth()>300){
            percent = Double.valueOf(300/Double.parseDouble(String.valueOf(inputImage.getWidth())));
        }
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File file = new File("C:\\Users\\i.ahmadov\\test.jpg");
        resize(inputStream, file, scaledWidth, scaledHeight);
        byteArrayOutputStream.close();
        return Files.readAllBytes(file.toPath());
    }
 
    /**
     * Test resizing images
     */
    /*public static void main(String[] args) {
        String inputImagePath = "D:/Photo/Puppy.jpg";
        String outputImagePath1 = "D:/Photo/Puppy_Fixed.jpg";
        String outputImagePath2 = "D:/Photo/Puppy_Smaller.jpg";
        String outputImagePath3 = "D:/Photo/Puppy_Bigger.jpg";
 
        try {
            // resize to a fixed width (not proportional)
            int scaledWidth = 1024;
            int scaledHeight = 768;
            ImageResizer.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
 
            // resize smaller by 50%
            double percent = 0.5;
            ImageResizer.resize(inputImagePath, outputImagePath2, percent);
 
            // resize bigger by 50%
            percent = 1.5;
            ImageResizer.resize(inputImagePath, outputImagePath3, percent);
 
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
    }*/

    public static byte[] compress(InputStream inputStream, String originalFileName) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);

        File compressedImageFile = new File(originalFileName);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.2f);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
        return Files.readAllBytes(compressedImageFile.toPath());
    }
 
}