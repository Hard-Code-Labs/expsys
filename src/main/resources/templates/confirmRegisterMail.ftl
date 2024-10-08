<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Confirm</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Flex:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(63.36deg, #0C1314 9.43%, #11594E 75.57%, #00BE99 99.87%);
            width: 100vw;
            height: 100vh;
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Roboto Flex', sans-serif;
        }
        main {
            width: 550px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            gap: 20px;
            background-color: #040f10b0;
            backdrop-filter: blur(10px);
            box-shadow: 0 0 10px #cdfeec;
            border: 1px solid white;
            border-radius: 50px;
            padding: 50px;
        }
        h1 {
            margin: 1px 0;
        }
        section {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        .button {
            width: 70%;
            padding: 15px;
            border-radius: 50px;
            border: none;
            background-color: #00BE99;
            color: white;
            font-size: large;
            font-weight: bold;
            text-decoration: none;
            opacity: 1;
            transition: opacity 0.5s;
        }
    </style>
</head>
<body>
<main>
    <h2>Hola ${perName}</h2>
    <h1>¡Bienvenido a Moneyatic! </h1>
    <h4>Para activar tu cuenta y empezar a disfrutar de nuestros servicios, solo necesitas confirmar tu dirección de correo electrónico. Haz clic en Confirmar</h4>

    <section >
        <a
                href=${URI}
                target="_blank"
                class="button"
        >
            Confirmar mi email
        </a>
    </section>

    <p>¡Gracias por unirte a Moneyatic!
        Si tienes alguna pregunta, no dudes en contactarnos.</p>
</main>
</body>
</html>