<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 30.08.2019
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>500</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/assets/css/demo4/pages/error/error-6.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/css/demo4/style.bundle.css" />" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="<c:url value="/assets/media/logos/sual-favicon.ico" />"/>
</head>
<body class="kt-page--loading-enabled kt-page--loading kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header--minimize-menu kt-header-mobile--fixed kt-subheader--enabled kt-subheader--transparent kt-page--loading">
<div class="kt-grid kt-grid--ver kt-grid--root kt-page">
    <div class="kt-grid__item kt-grid__item--fluid kt-grid  kt-error-v6"
         style="background-image: url(<c:url value="/assets/media/error/bg6.jpg" />);">
        <div class="kt-error_container">
            <div class="kt-error_subtitle kt-font-light">
                <h1>Sistem xətası...</h1>
            </div>
            <p class="kt-error_description kt-font-light">
                Sistem inzibatçısı ilə əlaqə saxlayın!
            </p>
        </div>
    </div>
</div>
<script src="<c:url value="/assets/vendors/general/jquery/dist/jquery.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/jquery-form/dist/jquery.form.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/bootstrap/dist/js/bootstrap.min.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/particles.min.js" />" type="text/javascript"></script> <!-- stats.js lib -->
<script src="<c:url value="/assets/vendors/general/jquery-validation/dist/jquery.validate.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/demo4/pages/login/login-general.js" />" type="text/javascript"></script>
<script>
    $(document).ready(function(){
        var login = $('#kt_login');
        var signInForm = login.find('.kt-login__signin form');
        signInForm.clearForm();
        signInForm.validate().resetForm();

        showErrorMsg(signInForm, 'error', 'İstifadəçi adı və ya Şifrəniz yanlışdır!');

    })
    particlesJS("particles-js", {
        "particles": {
            "number": {"value": 80, "density": {"enable": true, "value_area": 800}},
            "color": {"value": "#ffffff"},
            "shape": {
                "type": "circle",
                "stroke": {"width": 0, "color": "#000000"},
                "polygon": {"nb_sides": 3},
                "image": {"src": "img/github.svg", "width": 200, "height": 200}
            },
            "opacity": {
                "value": 0.7,
                "random": true,
                "anim": {"enable": false, "speed": 1, "opacity_min": 0.1, "sync": false}
            },
            "size": {
                "value": 4,
                "random": true,
                "anim": {"enable": false, "speed": 40, "size_min": 0.1, "sync": false}
            },
            "line_linked": {"enable": true, "distance": 150, "color": "#ffffff", "opacity": 0.4, "width": 1},
            "move": {
                "enable": true,
                "speed": 4,
                "direction": "none",
                "random": false,
                "straight": false,
                "out_mode": "out",
                "bounce": false,
                "attract": {"enable": false, "rotateX": 600, "rotateY": 1200}
            }
        },
        "interactivity": {
            "detect_on": "canvas",
            "events": {
                "onhover": {"enable": true, "mode": "repulse"},
                "onclick": {"enable": true, "mode": "push"},
                "resize": true
            },
            "modes": {
                "grab": {"distance": 400, "line_linked": {"opacity": 1}},
                "bubble": {"distance": 400, "size": 40, "duration": 2, "opacity": 8, "speed": 3},
                "repulse": {"distance": 200, "duration": 0.4},
                "push": {"particles_nb": 4},
                "remove": {"particles_nb": 2}
            }
        },
        "retina_detect": false
    });
    var count_particles, stats, update;
    stats = new Stats;
    stats.setMode(0);
    stats.domElement.style.position = 'absolute';
    stats.domElement.style.left = '0px';
    stats.domElement.style.top = '0px';
    document.body.appendChild(stats.domElement);
    count_particles = document.querySelector('.js-count-particles');
    update = function () {
        stats.begin();
        stats.end();
        if (window.pJSDom[0].pJS.particles && window.pJSDom[0].pJS.particles.array) {
            count_particles.innerText = window.pJSDom[0].pJS.particles.array.length;
        }
        requestAnimationFrame(update);
    };
    requestAnimationFrame(update);
</script>
</body>
</html>