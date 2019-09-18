<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>

    <title>Giriş</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700">--%>
    <link href="<c:url value="/assets/css/demo4/pages/login/login-2.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/css/demo4/style.bundle.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/vendors/custom/vendors/flaticon/flaticon.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/vendors/custom/vendors/flaticon2/flaticon.css" />" rel="stylesheet" type="text/css"/>

    <link rel="shortcut icon" href="<c:url value="/assets/media/logos/logo_sual_32_T28_icon.ico" />"/>
    <style>
        body {
            margin: 0;
            font: normal 75% Arial, Helvetica, sans-serif;
        }

        canvas {
            display: block;
            vertical-align: bottom;
            z-index:-1;
        }

        /* ---- particles.js container ---- */
        #particles-js {
            position: absolute;
            width: 100%;
            height: 100%;
            background-repeat: no-repeat;
            background-size: cover;
            background-position: 50% 50%;
        }

        /* ---- stats.js ---- */
        .count-particles {
            background: #000022;
            position: absolute;
            left: 0;
            color: #13E8E9;
            font-size: .8em;
            text-align: left;
            text-indent: 4px;
            line-height: 14px;
            padding-bottom: 2px;
            font-family: Helvetica, Arial, sans-serif;
            font-weight: bold;
        }

        .js-count-particles {
            font-size: 1.1em;
        }

        #stats, .count-particles {
            -webkit-user-select: none;
            margin-top: 5px;
            margin-left: 5px;
        }

        #stats {
            border-radius: 3px 3px 0 0;
            overflow: hidden;
        }

        .count-particles {
            border-radius: 0 0 3px 3px;
        }
    </style>
</head>
<body class="kt-page--loading-enabled kt-page--loading kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header--minimize-menu kt-header-mobile--fixed kt-subheader--enabled kt-subheader--transparent kt-page--loading">
<div id="particles-js"></div>
<div id="login-content" class="kt-grid kt-grid--ver kt-grid--root kt-page">
    <div class="kt-grid kt-grid--hor kt-grid--root kt-login kt-login--v2 kt-login--signin" id="kt_login">
        <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor"
             style="background-image: url('<c:url value="/assets/media/bg/bg-1.jpg"/>');">
            <div class="kt-grid__item kt-grid__item--fluid kt-login__wrapper">
                <div class="kt-login__container">
                    <div class="kt-login__logo">
                        <a href="#">
                            <img src="<c:url value="/assets/media/logos/logo-sual.png"/>">
                        </a>
                    </div>
                    <div class="kt-login__signin">
                        <div class="kt-login__head">
                            <h3 class="kt-login__title">Sistemə daxil olun</h3>
                        </div>
                        <form class="kt-form" method="post" action="/login">
                            <c:if test="${error eq 'true'}">
                                <div class="kt-alert kt-alert--outline alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><i class="flaticon2-delete" style="font-size: 12px;"></i></button>
                                    <span style="font-size: 16px"><c:out value="${message}"/></span>
                                </div>
                            </c:if>
                            <div class="input-group">
                                <input type="text" class="form-control" type="text" placeholder="İstifadəçi adı" name="username"
                                       autocomplete="off" style="background: rgba(67, 34, 167, 0.7); color: white; font-size: 16px;" />
                            </div>
                            <div class="input-group">
                                <input class="form-control" type="password" placeholder="Şifrə" name="password"
                                       style="background: rgba(67, 34, 167, 0.7); color: white; font-size: 16px;">
                            </div>
                            <div class="kt-login__actions input-group">
                                <button id="kt_login_signin_submit" class="btn btn-pill btn-block kt-login__btn-primary" style="z-index: 1000; margin-left: 20%; margin-right: 20%; font-size: 16px">Daxil ol
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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
    ;


</script>
</body>
</html>
