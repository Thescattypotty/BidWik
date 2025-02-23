package org.bidwik.bid.Util;

import org.springframework.stereotype.Component;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;

@Component
public class TwoFactorAuthenticationUtil {

    public String generateSecret(){
        return new DefaultSecretGenerator().generate();
    }

    public String generateQrCodeUri(String secret, String account){
        QrData data = new QrData.Builder()
            .label(account)
            .secret(secret)
            .issuer("Bidwik")
            .algorithm(HashingAlgorithm.SHA256)
            .digits(6)
            .period(30)
            .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];
        try {
            imageData = generator.generate(data);
        } catch (QrGenerationException e) {
            e.printStackTrace();
        }
        return Utils.getDataUriForImage(imageData, generator.getImageMimeType());
    }
    
    public boolean verifyCode(String secret, String code){
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA256, 6);
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return codeVerifier.isValidCode(secret, code);
    }
}
