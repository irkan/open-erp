<%@ page import="com.openerp.util.Constants" %>
<%@ page import="com.openerp.entity.User" %>
<%@ page import="com.openerp.entity.Person" %><%--
    Document   : menu
    Created on : Sep 3, 2019, 4:08:35 PM
    Author     : iahmadov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="kt_header" class="kt-header  kt-header--fixed " data-ktheader-minimize="on" style="opacity: 0.98; z-index: 10">
    <div class="kt-container ">
        <div class="kt-header__brand   kt-grid__item" id="kt_header_brand">
            <a class="kt-header__brand-logo" href="#">
                <img alt="Logo" src="<c:url value="/assets/media/logos/${logo}-40.png" />"
                     class="kt-header__brand-logo-default"/>
                <img alt="Logo" src="<c:url value="/assets/media/logos/${logo}-32.png" />"
                     class="kt-header__brand-logo-sticky"/>
            </a>
        </div>
        <button class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i class="la la-close"></i>
        </button>
        <div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
            <div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile ">
                <ul class="kt-menu__nav ">
                    <c:forEach var="t" items="${sessionScope.modules}" varStatus="loop">
                        <c:set var="parent" value="${utl:findParentModule(t)}"/>
                        <li class="kt-menu__item <c:out value="${t.children.size()>0 ? ' kt-menu__item--submenu ' : ' '}"/> <c:out value="${t.path==page ? 'kt-menu__item--here' : ' '}"/> kt-menu__item--rel"
                            data-ktmenu-submenu-toggle="click" aria-haspopup="true">
                            <a href="/route/sub${t.children.size()>0?'javascript:;':'/'.concat(parent.path).concat('/').concat(t.path)}"
                               class="kt-menu__link <c:out value="${t.children.size()>0 ? ' kt-menu__toggle ' : ' '}"/>">
                                <i class="<c:out value="${t.icon}" />"
                                   style="color: #d6d6d6;margin-right: 10px;font-size: 18px;"></i>
                                <span class="kt-menu__link-text"><c:out value="${t.name}"/></span>
                                <c:if test="${t.children.size()>0}">
                                    <i class="kt-menu__hor-arrow la la-angle-down"></i>
                                </c:if>
                            </a>
                            <c:if test="${t.children.size()>0}">
                                <div class="kt-menu__submenu kt-menu__submenu--classic kt-menu__submenu--left">
                                    <ul class="kt-menu__subnav">
                                        <li class="kt-menu__item  kt-menu__item--submenu"
                                            data-ktmenu-submenu-toggle="hover" aria-haspopup="true">
                                            <a href="/route/sub/<c:out value="${(parent.path).concat('/').concat(t.path)}" />"
                                               class="kt-menu__link">
                                                <i class="<c:out value="${t.icon}" />"
                                                   style="color: #d6d6d6;margin-right: 10px;font-size: 16px;"></i>
                                                <span class="kt-menu__link-text"><c:out value="${t.name}"/></span>
                                            </a>
                                        </li>
                                        <c:forEach var="p" items="${t.children}" varStatus="loop">
                                            <c:set var="subParent" value="${utl:findParentModule(p)}"/>
                                            <li class="kt-menu__item  kt-menu__item--submenu"
                                                data-ktmenu-submenu-toggle="hover" aria-haspopup="true">
                                                <a href="/route/sub/<c:out value="${subParent.path}" />/<c:out value="${p.path}" />"
                                                   class="kt-menu__link">
                                                    <i class="<c:out value="${p.icon}" />"
                                                       style="color: #d6d6d6;margin-right: 10px;font-size: 16px;"></i>
                                                    <span class="kt-menu__link-text"><c:out value="${p.name}"/></span>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </li>

                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="kt-header__topbar kt-grid__item">
            <div class="kt-header__topbar-item kt-header__topbar-item--search dropdown"
                 id="kt_quick_search_toggle">
                <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
				<span class="kt-header__topbar-icon">
					<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
                         height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <rect id="bound" x="0" y="0" width="24" height="24"/>
        <path d="M14.2928932,16.7071068 C13.9023689,16.3165825 13.9023689,15.6834175 14.2928932,15.2928932 C14.6834175,14.9023689 15.3165825,14.9023689 15.7071068,15.2928932 L19.7071068,19.2928932 C20.0976311,19.6834175 20.0976311,20.3165825 19.7071068,20.7071068 C19.3165825,21.0976311 18.6834175,21.0976311 18.2928932,20.7071068 L14.2928932,16.7071068 Z"
              id="Path-2" fill="#000000" fill-rule="nonzero" opacity="0.3"/>
        <path d="M11,16 C13.7614237,16 16,13.7614237 16,11 C16,8.23857625 13.7614237,6 11,6 C8.23857625,6 6,8.23857625 6,11 C6,13.7614237 8.23857625,16 11,16 Z M11,18 C7.13400675,18 4,14.8659932 4,11 C4,7.13400675 7.13400675,4 11,4 C14.8659932,4 18,7.13400675 18,11 C18,14.8659932 14.8659932,18 11,18 Z"
              id="Path" fill="#000000" fill-rule="nonzero"/>
    </g>
</svg>
				</span>
                </div>
                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-lg">
                    <div class="kt-quick-search kt-quick-search--dropdown kt-quick-search--result-compact"
                         id="kt_quick_search_dropdown">
                        <form method="get" class="kt-quick-search__form">
                            <div class="input-group">
                                <div class="input-group-prepend"><span class="input-group-text"><i
                                        class="flaticon2-search-1"></i></span></div>
                                <input type="text" class="form-control kt-quick-search__input"
                                       placeholder="Müştəri axtar...">
                                <div class="input-group-append"><span class="input-group-text"><i
                                        class="la la-close kt-quick-search__close"></i></span></div>
                            </div>
                        </form>
                        <div class="kt-quick-search__wrapper kt-scroll" data-scroll="true" data-height="325"
                             data-mobile-height="200">
                        </div>
                    </div>
                </div>
            </div>
            <div class="kt-header__topbar-item dropdown">
                <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
			<span class="kt-header__topbar-icon kt-pulse kt-pulse--light">
				<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <rect x="0" y="0" width="24" height="24"/>
        <path d="M13.5,21 L13.5,18 C13.5,17.4477153 13.0522847,17 12.5,17 L11.5,17 C10.9477153,17 10.5,17.4477153 10.5,18 L10.5,21 L5,21 L5,4 C5,2.8954305 5.8954305,2 7,2 L17,2 C18.1045695,2 19,2.8954305 19,4 L19,21 L13.5,21 Z M9,4 C8.44771525,4 8,4.44771525 8,5 L8,6 C8,6.55228475 8.44771525,7 9,7 L10,7 C10.5522847,7 11,6.55228475 11,6 L11,5 C11,4.44771525 10.5522847,4 10,4 L9,4 Z M14,4 C13.4477153,4 13,4.44771525 13,5 L13,6 C13,6.55228475 13.4477153,7 14,7 L15,7 C15.5522847,7 16,6.55228475 16,6 L16,5 C16,4.44771525 15.5522847,4 15,4 L14,4 Z M9,8 C8.44771525,8 8,8.44771525 8,9 L8,10 C8,10.5522847 8.44771525,11 9,11 L10,11 C10.5522847,11 11,10.5522847 11,10 L11,9 C11,8.44771525 10.5522847,8 10,8 L9,8 Z M9,12 C8.44771525,12 8,12.4477153 8,13 L8,14 C8,14.5522847 8.44771525,15 9,15 L10,15 C10.5522847,15 11,14.5522847 11,14 L11,13 C11,12.4477153 10.5522847,12 10,12 L9,12 Z M14,12 C13.4477153,12 13,12.4477153 13,13 L13,14 C13,14.5522847 13.4477153,15 14,15 L15,15 C15.5522847,15 16,14.5522847 16,14 L16,13 C16,12.4477153 15.5522847,12 15,12 L14,12 Z" fill="#000000"/>
        <rect fill="#FFFFFF" x="13" y="8" width="3" height="3" rx="1"/>
        <path d="M4,21 L20,21 C20.5522847,21 21,21.4477153 21,22 L21,22.4 C21,22.7313708 20.7313708,23 20.4,23 L3.6,23 C3.26862915,23 3,22.7313708 3,22.4 L3,22 C3,21.4477153 3.44771525,21 4,21 Z" fill="#000000" opacity="0.3"/>
    </g>
</svg>
				<span class="kt-pulse__ring"></span>
			</span>
                </div>
                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">
                    <form>
                        <div class="kt-head kt-head--skin-dark"
                             style="background-image: url(<c:url value="/assets/media/misc/bg-1.jpg"/>)">
                            <h3 class="kt-head__title">
                                Struktur
                            </h3>
                        </div>
                        <div class="tab-content">
                            <div class="tab-pane active show active"
                                 role="tabpanel">
                                <div class="kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll"
                                     data-scroll="true" data-height="300" data-mobile-height="300">
                                    <div class="kt-notification-v2">
                                        <c:set var="req" value="${pageContext.request}" />
                                        <c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 1, fn:length(req.requestURI)), req.contextPath)}" />
<%--                                        <c:url var="myUrl" value="${baseURL}/${MyID}"/>--%>
                                        <c:forEach var="t" items="${sessionScope.organizations}" varStatus="loop">
                                            <c:set var="hover" value=""/>
                                            <c:if test="${t.id==sessionScope.organization.id}">
                                                <c:set var="hover" value="a-link-active"/>
                                            </c:if>
                                            <a href="/route/sub/admin/<c:out value="${page}" />/org/<c:out value="${t.id}"/>" class="kt-notification-v2__item <c:out value="${hover}"/>">
                                                <div class="kt-notification-v2__item-icon">
                                                    <i class="flaticon2-box kt-font-danger"></i>
                                                </div>
                                                <div class="kt-notification-v2__itek-wrapper">
                                                    <div class="kt-notification-v2__item-title">
                                                        <c:out value="${t.name}"/>
                                                    </div>
                                                    <div class="kt-notification-v2__item-desc">
                                                        <c:out value="${t.description}"/>
                                                    </div>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <c:choose>
            <c:when test="${sessionScope.parent_modules.size()>1}">
            <div class="kt-header__topbar-item dropdown">
                <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
			<span class="kt-header__topbar-icon kt-pulse kt-pulse--light">
				<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
                     height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <rect id="bound" x="0" y="0" width="24" height="24"/>
                        <rect id="Rectangle-7" fill="#000000" x="4" y="4" width="7" height="7" rx="1.5"/>
                        <path d="M5.5,13 L9.5,13 C10.3284271,13 11,13.6715729 11,14.5 L11,18.5 C11,19.3284271 10.3284271,20 9.5,20 L5.5,20 C4.67157288,20 4,19.3284271 4,18.5 L4,14.5 C4,13.6715729 4.67157288,13 5.5,13 Z M14.5,4 L18.5,4 C19.3284271,4 20,4.67157288 20,5.5 L20,9.5 C20,10.3284271 19.3284271,11 18.5,11 L14.5,11 C13.6715729,11 13,10.3284271 13,9.5 L13,5.5 C13,4.67157288 13.6715729,4 14.5,4 Z M14.5,13 L18.5,13 C19.3284271,13 20,13.6715729 20,14.5 L20,18.5 C20,19.3284271 19.3284271,20 18.5,20 L14.5,20 C13.6715729,20 13,19.3284271 13,18.5 L13,14.5 C13,13.6715729 13.6715729,13 14.5,13 Z"
                              id="Combined-Shape" fill="#000000" opacity="0.3"/>
                    </g>
                </svg>
                <span class="kt-pulse__ring"></span>
							</span>
                </div>

                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">
                    <div class="kt-head kt-head--skin-dark"
                         style="background-image: url(<c:url value="/assets/media/misc/bg-1.jpg"/>)">
                        <h3 class="kt-head__title">
                            Funksionallıqlar
                        </h3>
                    </div>
                    <div class="kt-grid-nav kt-grid-nav--skin-light">
                    <c:forEach var="p" items="${sessionScope.parent_modules_map.keySet()}" varStatus="loop">
                    <div class="kt-grid-nav__row">
                        <c:forEach var="t" items="${sessionScope.parent_modules_map.get(p)}" varStatus="loop">
                            <a href="/route/<c:out value="${t.path}" />" class="kt-grid-nav__item">
                                <span class="kt-grid-nav__icon"><i class="<c:out value="${t.icon}" />"></i></span>
                                <span class="kt-grid-nav__title"><c:out value="${t.name}"/></span>
                                <span class="kt-grid-nav__desc"><c:out value="${t.description}"/></span>
                            </a>
                        </c:forEach>
                    </div>
                    </c:forEach>
                </div>
            </div>

        </div>
        </c:when>
        </c:choose>
        <div class="kt-header__topbar-item kt-header__topbar-item--quick-panel" data-toggle="kt-tooltip" title="Struktur" data-placement="right">
            <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
                <span class="kt-header__topbar-icon" id="kt_quick_panel_toggler_btn">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
                         height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
                    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                        <rect id="bound" x="0" y="0" width="24" height="24"/>
                        <path d="M2.56066017,10.6819805 L4.68198052,8.56066017 C5.26776695,7.97487373 6.21751442,7.97487373 6.80330086,8.56066017 L8.9246212,10.6819805 C9.51040764,11.267767 9.51040764,12.2175144 8.9246212,12.8033009 L6.80330086,14.9246212 C6.21751442,15.5104076 5.26776695,15.5104076 4.68198052,14.9246212 L2.56066017,12.8033009 C1.97487373,12.2175144 1.97487373,11.267767 2.56066017,10.6819805 Z M14.5606602,10.6819805 L16.6819805,8.56066017 C17.267767,7.97487373 18.2175144,7.97487373 18.8033009,8.56066017 L20.9246212,10.6819805 C21.5104076,11.267767 21.5104076,12.2175144 20.9246212,12.8033009 L18.8033009,14.9246212 C18.2175144,15.5104076 17.267767,15.5104076 16.6819805,14.9246212 L14.5606602,12.8033009 C13.9748737,12.2175144 13.9748737,11.267767 14.5606602,10.6819805 Z"
                              id="Combined-Shape" fill="#000000" opacity="0.3"/>
                        <path d="M8.56066017,16.6819805 L10.6819805,14.5606602 C11.267767,13.9748737 12.2175144,13.9748737 12.8033009,14.5606602 L14.9246212,16.6819805 C15.5104076,17.267767 15.5104076,18.2175144 14.9246212,18.8033009 L12.8033009,20.9246212 C12.2175144,21.5104076 11.267767,21.5104076 10.6819805,20.9246212 L8.56066017,18.8033009 C7.97487373,18.2175144 7.97487373,17.267767 8.56066017,16.6819805 Z M8.56066017,4.68198052 L10.6819805,2.56066017 C11.267767,1.97487373 12.2175144,1.97487373 12.8033009,2.56066017 L14.9246212,4.68198052 C15.5104076,5.26776695 15.5104076,6.21751442 14.9246212,6.80330086 L12.8033009,8.9246212 C12.2175144,9.51040764 11.267767,9.51040764 10.6819805,8.9246212 L8.56066017,6.80330086 C7.97487373,6.21751442 7.97487373,5.26776695 8.56066017,4.68198052 Z"
                              id="Combined-Shape" fill="#000000"/>
                    </g>
                </svg>
                </span>
            </div>
        </div>
        <div class="kt-header__topbar-item kt-header__topbar-item--langs">
            <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
			<span class="kt-header__topbar-icon">
				<img class="" src="<c:url value="/assets/media/flags/021-azerbaijan.png" />" alt=""/>
			</span>
            </div>
            <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim">
                <ul class="kt-nav kt-margin-t-10 kt-margin-b-10">
                    <li class="kt-nav__item kt-nav__item--active">
                        <a href="#" class="kt-nav__link">
                                            <span class="kt-nav__link-icon"><img
                                                    src="<c:url value="/assets/media/flags/021-azerbaijan.png" />"
                                                    alt=""/></span>
                            <span class="kt-nav__link-text">Azərbaycan</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!--end: Language bar -->

            <div id="kt_quick_panel" class="kt-quick-panel">
                <a href="#" class="kt-quick-panel__close" id="kt_quick_panel_close_btn"><i class="flaticon2-delete"></i></a>
                <div class="kt-quick-panel__nav">
                    <ul class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-3x nav-tabs-line-brand  kt-notification-item-padding-x" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#kt_quick_panel_tab_notifications" role="tab">Notifications</a>
                        </li>
                        <li class="nav-item active">
                            <a class="nav-link" data-toggle="tab" href="#kt_quick_panel_tab_logs" role="tab">Audit Logs</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kt_quick_panel_tab_settings" role="tab">Settings</a>
                        </li>
                    </ul>
                </div>
                <div class="kt-quick-panel__content">
                    <div class="tab-content">
                        <div class="tab-pane fade show kt-scroll active" id="kt_quick_panel_tab_notifications" role="tabpanel">
                            <div class="kt-notification">
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-line-chart kt-font-success"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New order has been received
                                        </div>
                                        <div class="kt-notification__item-time">
                                            2 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-box-1 kt-font-brand"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New customer is registered
                                        </div>
                                        <div class="kt-notification__item-time">
                                            3 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-chart2 kt-font-danger"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            Application has been approved
                                        </div>
                                        <div class="kt-notification__item-time">
                                            3 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-image-file kt-font-warning"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New file has been uploaded
                                        </div>
                                        <div class="kt-notification__item-time">
                                            5 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-drop kt-font-info"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New user feedback received
                                        </div>
                                        <div class="kt-notification__item-time">
                                            8 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-pie-chart-2 kt-font-success"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            System reboot has been successfully completed
                                        </div>
                                        <div class="kt-notification__item-time">
                                            12 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-favourite kt-font-danger"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New order has been placed
                                        </div>
                                        <div class="kt-notification__item-time">
                                            15 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item kt-notification__item--read">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-safe kt-font-primary"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            Company meeting canceled
                                        </div>
                                        <div class="kt-notification__item-time">
                                            19 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-psd kt-font-success"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New report has been received
                                        </div>
                                        <div class="kt-notification__item-time">
                                            23 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon-download-1 kt-font-danger"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            Finance report has been generated
                                        </div>
                                        <div class="kt-notification__item-time">
                                            25 hrs ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon-security kt-font-warning"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New customer comment recieved
                                        </div>
                                        <div class="kt-notification__item-time">
                                            2 days ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification__item">
                                    <div class="kt-notification__item-icon">
                                        <i class="flaticon2-pie-chart kt-font-warning"></i>
                                    </div>
                                    <div class="kt-notification__item-details">
                                        <div class="kt-notification__item-title">
                                            New customer is registered
                                        </div>
                                        <div class="kt-notification__item-time">
                                            3 days ago
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="tab-pane fade kt-scroll" id="kt_quick_panel_tab_logs" role="tabpanel">
                            <div class="kt-notification-v2">
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon-bell kt-font-brand"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            5 new user generated report
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Reports based on sales
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon2-box kt-font-danger"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            2 new items submited
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            by Grog John
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon-psd kt-font-brand"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            79 PSD files generated
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Reports based on sales
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon2-supermarket kt-font-warning"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            $2900 worth producucts sold
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Total 234 items
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon-paper-plane-1 kt-font-success"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            4.5h-avarage response time
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Fostest is Barry
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon2-information kt-font-danger"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            Database server is down
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            10 mins ago
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon2-mail-1 kt-font-brand"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            System report has been generated
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Fostest is Barry
                                        </div>
                                    </div>
                                </a>
                                <a href="#" class="kt-notification-v2__item">
                                    <div class="kt-notification-v2__item-icon">
                                        <i class="flaticon2-hangouts-logo kt-font-warning"></i>
                                    </div>
                                    <div class="kt-notification-v2__itek-wrapper">
                                        <div class="kt-notification-v2__item-title">
                                            4.5h-avarage response time
                                        </div>
                                        <div class="kt-notification-v2__item-desc">
                                            Fostest is Barry
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="tab-pane kt-quick-panel__content-padding-x fade kt-scroll" id="kt_quick_panel_tab_settings" role="tabpanel">
                            <form class="kt-form">
                                <div class="kt-heading kt-heading--sm kt-heading--space-sm">Customer Care</div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Enable Notifications:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--success kt-switch--sm">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_1">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Enable Case Tracking:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--success kt-switch--sm">
										<label>
											<input type="checkbox" name="quick_panel_notifications_2">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-last form-group-xs row">
                                    <label class="col-8 col-form-label">Support Portal:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--success kt-switch--sm">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_2">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="kt-separator kt-separator--space-md kt-separator--border-dashed"></div>
                                <div class="kt-heading kt-heading--sm kt-heading--space-sm">Reports</div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Generate Reports:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--danger">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_3">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Enable Report Export:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--danger">
										<label>
											<input type="checkbox" name="quick_panel_notifications_3">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-last form-group-xs row">
                                    <label class="col-8 col-form-label">Allow Data Collection:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--danger">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_4">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="kt-separator kt-separator--space-md kt-separator--border-dashed"></div>
                                <div class="kt-heading kt-heading--sm kt-heading--space-sm">Memebers</div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Enable Member singup:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--brand">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_5">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-xs row">
                                    <label class="col-8 col-form-label">Allow User Feedbacks:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--brand">
										<label>
											<input type="checkbox" name="quick_panel_notifications_5">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                                <div class="form-group form-group-last form-group-xs row">
                                    <label class="col-8 col-form-label">Enable Customer Portal:</label>
                                    <div class="col-4 kt-align-right">
									<span class="kt-switch kt-switch--sm kt-switch--brand">
										<label>
											<input type="checkbox" checked="checked" name="quick_panel_notifications_6">
											<span></span>
										</label>
									</span>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        <!--begin: User bar -->
        <div class="kt-header__topbar-item kt-header__topbar-item--user">
            <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="10px,0px">
                <span class="kt-header__topbar-welcome">Salam,</span>
                <span class="kt-header__topbar-username"> <c:out
                        value="${sessionScope.user.employee.person.firstName}"/> </span>

                <span class="kt-header__topbar-icon"><b><c:out
                        value="${fn:substring(sessionScope.user.employee.person.firstName, 0,1)}"/></b></span>
                <img alt="Pic" src="<c:url value="/assets/media/users/300_21.jpg" />" class="kt-hidden"/>
            </div>
            <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">
                <div class="kt-user-card kt-user-card--skin-dark kt-notification-item-padding-x"
                     style="background-image: url(<c:url value="/assets/media/misc/bg-1.jpg"/>)">
                    <div class="kt-user-card__avatar">
                        <img class="kt-hidden" alt="Pic" src="<c:url value="/assets/media/users/300_25.jpg" />"/>
                        <span class="kt-badge kt-badge--lg kt-badge--rounded kt-badge--bold kt-font-success"><c:out
                                value="${fn:substring(sessionScope.user.employee.person.firstName, 0,1)}"/></span>
                    </div>
                    <div class="kt-user-card__name">
                        <c:out value="${sessionScope.user.employee.person.firstName}"/>&nbsp;<c:out
                            value="${sessionScope.user.employee.person.lastName}"/>
                    </div>
                    <div class="kt-user-card__badge">
                        <span class="btn btn-success btn-sm btn-bold btn-font-md">23 mesaj</span>
                    </div>
                </div>
                <!--end: Head -->

                <!--begin: Navigation -->
                <div class="kt-notification">
                    <a href="/profile"
                       class="kt-notification__item">
                        <div class="kt-notification__item-icon">
                            <i class="flaticon2-calendar-3 kt-font-success"></i>
                        </div>
                        <div class="kt-notification__item-details">
                            <div class="kt-notification__item-title kt-font-bold">
                                Mənim profilim
                            </div>
                            <div class="kt-notification__item-time">
                                Hesab məlumatları və sazlamaları
                            </div>
                        </div>
                    </a>
                    <div class="kt-notification__custom kt-space-between">
                        <a href="/logout"
                           class="btn btn-label btn-label-brand btn-sm btn-bold">Çıxış</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
