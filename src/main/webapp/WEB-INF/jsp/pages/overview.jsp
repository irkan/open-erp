<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="kt-grid kt-grid--desktop kt-grid--ver kt-grid--ver-desktop kt-app">
        <button class="kt-app__aside-close" id="kt_user_profile_aside_close">
            <i class="la la-close"></i>
        </button>
        <div class="kt-grid__item kt-app__toggle kt-app__aside" id="kt_user_profile_aside">
            <%@include file="profile-aside.jsp" %>
        </div>
        <div class="kt-grid__item kt-grid__item--fluid kt-app__content">
            <div class="row">
                <div class="col-xl-6">
                    <div class="kt-portlet kt-portlet--height-fluid">
                        <div class="kt-portlet__head">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">
                                    Order Statistics
                                </h3>
                            </div>
                            <div class="kt-portlet__head-toolbar">
                                <a href="#" class="btn btn-label-brand btn-bold btn-sm dropdown-toggle" data-toggle="dropdown">
                                    Export
                                </a>
                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                    <!--begin::Nav-->
                                    <ul class="kt-nav">
                                        <li class="kt-nav__head">
                                            Export Options
                                            <span data-toggle="kt-tooltip" data-placement="right" title="Click to learn more...">
            <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon kt-svg-icon--brand kt-svg-icon--md1">
    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
        <rect id="bound" x="0" y="0" width="24" height="24"/>
        <circle id="Oval-5" fill="#000000" opacity="0.3" cx="12" cy="12" r="10"/>
        <rect id="Rectangle-9" fill="#000000" x="11" y="10" width="2" height="7" rx="1"/>
        <rect id="Rectangle-9-Copy" fill="#000000" x="11" y="7" width="2" height="2" rx="1"/>
    </g>
</svg>        </span>
                                        </li>
                                        <li class="kt-nav__separator"></li>
                                        <li class="kt-nav__item">
                                            <a href="#" class="kt-nav__link">
                                                <i class="kt-nav__link-icon flaticon2-drop"></i>
                                                <span class="kt-nav__link-text">Activity</span>
                                            </a>
                                        </li>
                                        <li class="kt-nav__item">
                                            <a href="#" class="kt-nav__link">
                                                <i class="kt-nav__link-icon flaticon2-calendar-8"></i>
                                                <span class="kt-nav__link-text">FAQ</span>
                                            </a>
                                        </li>
                                        <li class="kt-nav__item">
                                            <a href="#" class="kt-nav__link">
                                                <i class="kt-nav__link-icon flaticon2-telegram-logo"></i>
                                                <span class="kt-nav__link-text">Settings</span>
                                            </a>
                                        </li>
                                        <li class="kt-nav__item">
                                            <a href="#" class="kt-nav__link">
                                                <i class="kt-nav__link-icon flaticon2-new-email"></i>
                                                <span class="kt-nav__link-text">Support</span>
                                                <span class="kt-nav__link-badge">
                <span class="kt-badge kt-badge--success kt-badge--rounded">5</span>
            </span>
                                            </a>
                                        </li>
                                        <li class="kt-nav__separator"></li>
                                        <li class="kt-nav__foot">
                                            <a class="btn btn-label-danger btn-bold btn-sm" href="#">Upgrade plan</a>
                                            <a class="btn btn-clean btn-bold btn-sm" href="#" data-toggle="kt-tooltip" data-placement="right" title="Click to learn more...">Learn more</a>
                                        </li>
                                    </ul>
                                    <!--end::Nav-->			</div>
                            </div>
                        </div>
                        <div class="kt-portlet__body kt-portlet__body--fluid">
                            <div class="kt-widget12">
                                <div class="kt-widget12__content">
                                    <div class="kt-widget12__item">
                                        <div class="kt-widget12__info">
                                            <span class="kt-widget12__desc">İllik avans</span>
                                            <span class="kt-widget12__value">$400,000</span>
                                        </div>

                                        <div class="kt-widget12__info">
                                            <span class="kt-widget12__desc">Son avans tarixi</span>
                                            <span class="kt-widget12__value">July 24,2019</span>
                                        </div>
                                    </div>
                                    <div class="kt-widget12__item">
                                        <div class="kt-widget12__info">
                                            <span class="kt-widget12__desc">Qalıq avans limiti</span>
                                            <span class="kt-widget12__value">$60M</span>
                                        </div>
                                        <div class="kt-widget12__info">
                                            <span class="kt-widget12__desc">Revenue Margin</span>
                                            <div class="kt-widget12__progress">
                                                <div class="progress kt-progress--sm">
                                                    <div class="progress-bar kt-bg-brand" role="progressbar" style="width: 40%;" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                                </div>
                                                <span class="kt-widget12__stat">
                                40%
                            </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="kt-widget12__chart" style="height:250px;">
                                    <canvas id="kt_chart_order_statistics"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--end:: Widgets/Order Statistics-->            </div>
                <div class="col-xl-6">
                    <!--begin:: Widgets/Tasks -->
                    <div class="kt-portlet kt-portlet--tabs kt-portlet--height-fluid">
                        <div class="kt-portlet__head">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">
                                    Tasks
                                </h3>
                            </div>
                            <div class="kt-portlet__head-toolbar">
                                <ul class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-brand" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#kt_widget2_tab1_content" role="tab">
                                            Today
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#kt_widget2_tab2_content" role="tab">
                                            Week
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#kt_widget2_tab3_content" role="tab">
                                            Month
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="kt-portlet__body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="kt_widget2_tab1_content">
                                    <div class="kt-widget2">
                                        <div class="kt-widget2__item kt-widget2__item--primary">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Great  Again.Lorem Ipsum Amet
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--warning">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Prepare Docs For Metting On Monday
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Sean
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--brand">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Widgets Development. Estudiat Communy Elit
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Aziko
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--success">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Development. Lorem Ipsum
                                                </a>
                                                <a class="kt-widget2__username">
                                                    By James
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--danger">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>


                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--info">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Sean
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane" id="kt_widget2_tab2_content">
                                    <div class="kt-widget2">
                                        <div class="kt-widget2__item kt-widget2__item--success">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Development.Lorem Ipsum
                                                </a>
                                                <a class="kt-widget2__username">
                                                    By James
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--warning">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Prepare Docs For Metting On Monday
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Sean
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--danger">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>


                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--primary">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>

                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Great  Again.Lorem Ipsum Amet
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>

                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--info">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Sean
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--brand">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Widgets Development.Estudiat Communy Elit
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Aziko
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="tab-pane" id="kt_widget2_tab3_content">
                                    <div class="kt-widget2">

                                        <div class="kt-widget2__item kt-widget2__item--warning">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Development. Lorem Ipsum
                                                </a>
                                                <a class="kt-widget2__username">
                                                    By James
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--danger">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--brand">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Widgets Development.Estudiat Communy Elit
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Aziko
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--info">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Sean
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--success">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Completa Financial Report For Emirates Airlines
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                        <div class="kt-widget2__item kt-widget2__item--primary">
                                            <div class="kt-widget2__checkbox">
                                                <label class="kt-checkbox kt-checkbox--solid kt-checkbox--single">
                                                    <input type="checkbox">
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="kt-widget2__info">
                                                <a href="#" class="kt-widget2__title">
                                                    Make Metronic Great  Again.Lorem Ipsum Amet
                                                </a>
                                                <a href="#" class="kt-widget2__username">
                                                    By Bob
                                                </a>
                                            </div>
                                            <div class="kt-widget2__actions">
                                                <a href="#" class="btn btn-clean btn-sm btn-icon btn-icon-md" data-toggle="dropdown">
                                                    <i class="flaticon-more-1"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right">
                                                    <ul class="kt-nav">
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-line-chart"></i>
                                                                <span class="kt-nav__link-text">Reports</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-send"></i>
                                                                <span class="kt-nav__link-text">Messages</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-pie-chart-1"></i>
                                                                <span class="kt-nav__link-text">Charts</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-avatar"></i>
                                                                <span class="kt-nav__link-text">Members</span>
                                                            </a>
                                                        </li>
                                                        <li class="kt-nav__item">
                                                            <a href="#" class="kt-nav__link">
                                                                <i class="kt-nav__link-icon flaticon2-settings"></i>
                                                                <span class="kt-nav__link-text">Settings</span>
                                                            </a>
                                                        </li>
                                                    </ul>							</div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>





        </div>
    </div>
</div>
<script src="<c:url value="/assets/vendors/general/morris.js/morris.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/vendors/general/chart.js/dist/Chart.bundle.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/demo4/pages/dashboard.js" />" type="text/javascript"></script>
<script src="<c:url value="/assets/js/demo4/pages/custom/user/profile.js" />" type="text/javascript"></script>