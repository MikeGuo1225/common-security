package com.chentongwei.security.core.validate.image;

import com.chentongwei.security.core.validate.code.ValidateCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图形验证码
 *
 * @author chentongwei@bshf360.com 2018-03-27 12:52
 */
public class ImageCode extends ValidateCode {
    private static final long serialVersionUID = -7066853836932243479L;

    private String image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", byteArrayStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.image = new sun.misc.BASE64Encoder().encodeBuffer(byteArrayStream.toByteArray());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
