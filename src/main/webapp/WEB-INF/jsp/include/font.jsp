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

