<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Confirm</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body style="margin: 0; padding: 0; background-color: #B0B3B5; color: white; font-family: 'Roboto', sans-serif;">
<table role="presentation" width="100%" cellpadding="0" cellspacing="0" style="background-color: #B0B3B5; text-align: center; padding: 20px;">
    <tr>
        <td align="center">
            <table role="presentation" width="550" cellpadding="0" cellspacing="0" style="background-color: #122629; border-radius: 16px; overflow: hidden; box-shadow: 0px 4px 16px rgba(0, 0, 0, 0.25);">
                <tr>
                    <td style="padding: 40px; text-align: center;">
                        <!-- Saludo -->
                        <h1 style="margin: 0; color: #00BE99; font-weight: 700; font-size: 24px;">¡Bienvenido a Moneyatic!</h1>
                        <p style="color: #D9D9D9; font-size: 16px; margin-top: 20px; line-height: 1.5;">
                            Hola ${perName}, para activar tu cuenta y empezar a disfrutar de nuestros servicios, solo necesitas confirmar tu dirección de correo electrónico.
                            <br>Haz clic en el botón a continuación:
                        </p>
                        <!-- Botón -->
                        <a href="${URI}" target="_blank" style="display: inline-block; margin-top: 30px; padding: 14px 32px; background-color: #00BE99; color: white; text-decoration: none; border-radius: 32px; font-weight: 700; font-size: 16px; transition: background-color 0.3s ease, transform 0.2s ease; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.25);">
                            Confirmar mi email
                        </a>
                        <p style="color: #B0B0B0; font-size: 14px; margin-top: 30px;">
                            Si tienes alguna pregunta, no dudes en <a href="mailto:soporte@moneyatic.com" style="color: #00BE99; text-decoration: none;">contactarnos</a>.
                        </p>
                    </td>
                </tr>
                <!-- Pie -->
                <tr>
                    <td style="background-color: #0E1E20; padding: 16px; text-align: center; color: #7F8C8D; font-size: 12px;">
                        © 2024 Moneyatic. Todos los derechos reservados.
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>