<%-- 
    Document   : header
    Created on : Sep 3, 2019, 11:09:58 AM
    Author     : iahmadov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>


    @font-face {
        font-family: 'Gilroy';
        src: url('<c:url value="/assets/fonts/Gilroy/Gilroy-Regular.eot"/>');
        src: url('<c:url value="/assets/fonts/Gilroy/Gilroy-Regular.eot?#iefix"/>') format('embedded-opentype'),
        url('<c:url value="/assets/fonts/Gilroy/Gilroy-Regular.woff"/>') format('woff'),
        url('<c:url value="/assets/fonts/Gilroy/Gilroy-Regular.ttf"/>') format('truetype');
        font-weight: normal;
        font-style: normal;
    }

    @font-face {
        font-family: 'Fira Sans';
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.eot"/>");
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.eot?#iefix"/>") format("embedded-opentype"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.woff2"/>") format("woff2"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.woff"/>") format("woff"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.ttf"/>") format("truetype"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.svg#fira_sanslight"/>") format("svg");
        font-weight: 300;
        font-style: normal; }

    @font-face {
        font-family: 'Fira Sans';
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.eot"/>");
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.woff2"/>") format("woff2"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.woff"/>") format("woff"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.ttf"/>") format("truetype"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-bold-webfont.svg#fira_sansbold"/>") format("svg");
        font-weight: 700;
        font-style: normal; }

    @font-face {
        font-family: 'latoregular';
        /*src: url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.eot"/>');
          src: url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.eot?#iefix"/>') format('embedded-opentype'),
               url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.woff2"/>') format('woff2'),
               url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.woff"/>') format('woff'),
               url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.ttf"/>') format('truetype'),
               url('<c:url value="/assets/fonts/Lato/lato-regular-webfont.svg#latoregular"/>') format('svg');*/
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.eot"/>");
        src: url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.eot?#iefix"/>") format("embedded-opentype"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.woff2"/>") format("woff2"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.woff"/>") format("woff"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.ttf"/>") format("truetype"), 
        url("<c:url value="/assets/fonts/FiraSans/firasans-light-webfont.svg#fira_sanslight"/>") format("svg");
        font-weight: normal;
        font-style: normal; }

</style>

<style>
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.f6b2d59abab228c1ce63.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.f6b2d59abab228c1ce63.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.a760d255b899508203a0.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.96a3308f07ac10f14243.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.064e9b28f1df87e4d098.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Light.c69691e0d988b1485dd7.svg#ProximaNova-Light"/>") format("svg");
        font-weight:300;
        font-style:normal
    }
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.c97fac8b0aef1f0cb25f.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.c97fac8b0aef1f0cb25f.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.9cc50a2dee82047d2056.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.40349f1823de452db7e3.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.a2cf50e8becf25123996.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Regular.4e0aeb5b233a60aa1891.svg#ProximaNova-Regular"/>") format("svg");
        font-weight:400;
        font-style:normal
    }
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.9fa9436752c9594d2d67.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.9fa9436752c9594d2d67.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.8e03dbacbb30a1e9b6a9.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.e456a4b5241c4519a82b.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.987d2c3e1ad4cd07a53f.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Medium.4499cb860aed77d5035f.svg#ProximaNova-Medium"/>") format("svg");
        font-weight:500;
        font-style:normal
    }
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.480c50f118d7546dad08.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.480c50f118d7546dad08.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.f376e9e4baad0fc9fefd.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.c41e488ba11cd7f5c385.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.735f9bd7bd1a3bd75fc9.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-SemiBold.36c06595880b80b7617e.svg#ProximaNova-SemiBold"/>") format("svg");
        font-weight:600;
        font-style:normal
    }
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.8f40d37b3954d2413aef.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.8f40d37b3954d2413aef.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.8a9e31b8fb995bdd4216.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.d183a5184e6459eafeeb.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.a23e335f71e424cf144f.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-Bold.afabef99d99678a385b9.svg#ProximaNova-Bold"/>") format("svg");
        font-weight:700;
        font-style:normal
    }
    @font-face{
        font-family:'Proxima Nova';
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.323394eaa77a13355b9e.eot"/>");
        src:url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.323394eaa77a13355b9e.eot?#iefix"/>") format("embedded-opentype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.48d9fae9c89b7bf31357.woff2"/>") format("woff2"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.6741392b46cf5a044480.woff"/>") format("woff"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.3a82a506e6bd9cd87847.ttf"/>") format("truetype"),
        url("<c:url value="/assets/fonts/ProximaNova/ProximaNova-ExtraBold.54370e033be4004e283c.svg#ProximaNova-ExtraBold"/>") format("svg");
        font-weight:900;
        font-style:normal
    }

</style>

