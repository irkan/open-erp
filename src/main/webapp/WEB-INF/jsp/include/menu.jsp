<%@ page import="com.openerp.util.Constants" %>
<%@ page import="com.openerp.entity.User" %>
<%@ page import="com.openerp.entity.Person" %><%--
    Document   : menu
    Created on : Sep 3, 2019, 4:08:35 PM
    Author     : iahmadov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="kt_header" class="kt-header  kt-header--fixed " data-ktheader-minimize="on">
    <div class="kt-container ">
        <div class="kt-header__brand   kt-grid__item" id="kt_header_brand">
            <a class="kt-header__brand-logo" href="#">
                <img alt="Logo" src="<c:url value="/assets/media/logos/logo-sual-40.png" />"
                     class="kt-header__brand-logo-default"/>
                <img alt="Logo" src="<c:url value="/assets/media/logos/logo-sual-32.png" />"
                     class="kt-header__brand-logo-sticky"/>
            </a>
        </div>
        <button class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i
                class="la la-close"></i></button>
        <div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
            <div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile ">
                <ul class="kt-menu__nav ">
                    <c:forEach var="t" items="${sessionScope.modules}" varStatus="loop">
                        <li class="kt-menu__item kt-menu__item--here kt-menu__item--rel" <%--kt-menu__item--here--%>>
                            <a href="/<c:out value="${t.module.path}" />/<c:out value="${t.path}" />" class="kt-menu__link">
                                <i class="la <c:out value="${t.icon.name}" />" style="color: #d6d6d6;margin-right: 10px;font-size: 16px;"></i>
                                <span class="kt-menu__link-text"><c:out value="${t.name}" /></span>
                            </a>
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
				<span class="kt-pulse__ring"></span>
			</span>
                </div>
                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">
                    <form>
                        <div class="kt-head kt-head--skin-dark kt-head--fit-x kt-head--fit-b"
                             style="background-image: url(<c:url value="/assets/media/misc/bg-1.jpg"/>)">
                            <h3 class="kt-head__title">
                                User Notifications
                                &nbsp;
                                <span class="btn btn-success btn-sm btn-bold btn-font-md">23 new</span>
                            </h3>

                            <ul class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-3x nav-tabs-line-success kt-notification-item-padding-x"
                                role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active show" data-toggle="tab"
                                       href="#topbar_notifications_notifications" role="tab"
                                       aria-selected="true">Xəbərdarlıqlar</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-toggle="tab" href="#topbar_notifications_logs"
                                       role="tab" aria-selected="false">Loqlar</a>
                                </li>
                            </ul>
                        </div>
                        <div class="tab-content">
                            <div class="tab-pane active show" id="topbar_notifications_notifications"
                                 role="tabpanel">
                                <div class="kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll"
                                     data-scroll="true" data-height="300" data-mobile-height="200">


                                </div>
                            </div>
                            <div class="tab-pane" id="topbar_notifications_logs" role="tabpanel">
                                <div class="kt-grid kt-grid--ver" style="min-height: 200px;">
                                    <div class="kt-grid kt-grid--hor kt-grid__item kt-grid__item--fluid kt-grid__item--middle">
                                        <div class="kt-grid__item kt-grid__item--middle kt-align-center">
                                            All caught up!
                                            <br>No new notifications.
                                        </div>
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
			<span class="kt-header__topbar-icon">
				<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px"
                     height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <rect id="bound" x="0" y="0" width="24" height="24"/>
        <rect id="Rectangle-7" fill="#000000" x="4" y="4" width="7" height="7" rx="1.5"/>
        <path d="M5.5,13 L9.5,13 C10.3284271,13 11,13.6715729 11,14.5 L11,18.5 C11,19.3284271 10.3284271,20 9.5,20 L5.5,20 C4.67157288,20 4,19.3284271 4,18.5 L4,14.5 C4,13.6715729 4.67157288,13 5.5,13 Z M14.5,4 L18.5,4 C19.3284271,4 20,4.67157288 20,5.5 L20,9.5 C20,10.3284271 19.3284271,11 18.5,11 L14.5,11 C13.6715729,11 13,10.3284271 13,9.5 L13,5.5 C13,4.67157288 13.6715729,4 14.5,4 Z M14.5,13 L18.5,13 C19.3284271,13 20,13.6715729 20,14.5 L20,18.5 C20,19.3284271 19.3284271,20 18.5,20 L14.5,20 C13.6715729,20 13,19.3284271 13,18.5 L13,14.5 C13,13.6715729 13.6715729,13 14.5,13 Z"
              id="Combined-Shape" fill="#000000" opacity="0.3"/>
    </g>
</svg>
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
                    <div class="kt-grid-nav__row">
<c:forEach var="t" items="${sessionScope.parent_modules}" varStatus="loop">

    <a href="/route/<c:out value="${t.path}" />" class="kt-grid-nav__item">
                            <span class="kt-grid-nav__icon">
                                <i class="la <c:out value="${t.icon.name}" />"></i>
                            </span>
        <span class="kt-grid-nav__title"><c:out value="${t.name}" /></span>
        <span class="kt-grid-nav__desc"><c:out value="${t.description}" /></span>
    </a>

    <%--<c:choose>
        <c:when test="${t.moduleOperation.module.module eq null}">

                <a href="/route/<c:out value="${t.moduleOperation.module.path}" />" class="kt-grid-nav__item">
                            <span class="kt-grid-nav__icon">
                                <i class="la <c:out value="${t.moduleOperation.module.icon.name}" />"></i>
                            </span>
                    <span class="kt-grid-nav__title"><c:out value="${t.moduleOperation.module.name}" /></span>
                    <span class="kt-grid-nav__desc"><c:out value="${t.moduleOperation.module.description}" /></span>
                </a>

        </c:when>
    </c:choose>--%>
</c:forEach>
                    </div>
                </div>
            </div>

            </div>
            </c:when>
            </c:choose>
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
                        <a href="demo4/custom/apps/user/profile-1/personal-information.html"
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
