<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="<c:url value="/assets/css/demo4/pages/wizard/wizard-1.css" />" rel="stylesheet" type="text/css"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    Kontenti kodlayın!
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="padding: 0;">

            </div>
            <%--<div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>--%>


            <div class="kt-grid  kt-wizard-v1 kt-wizard-v1--white" id="kt_wizard_v1" data-ktwizard-state="step-first">
                <div class="kt-grid__item">

                    <!--begin: Form Wizard Nav -->
                    <div class="kt-wizard-v1__nav">
                        <div class="kt-wizard-v1__nav-items">
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step" data-ktwizard-state="current">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-list"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        1. Müştəri
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-bus-stop"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        2. Əlaqə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-responsive"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        3. İnventar
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-price-tag"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        4. Ödəmə
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-truck"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        5. Satıcı
                                    </div>
                                </div>
                            </div>
                            <div class="kt-wizard-v1__nav-item" data-ktwizard-type="step">
                                <div class="kt-wizard-v1__nav-body">
                                    <div class="kt-wizard-v1__nav-icon">
                                        <i class="flaticon-globe"></i>
                                    </div>
                                    <div class="kt-wizard-v1__nav-label">
                                        6. Baxış
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--end: Form Wizard Nav -->
                </div>
                <div class="kt-grid__item kt-grid__item--fluid kt-wizard-v1__wrapper">
                <form:form modelAttribute="form" id="kt_form" method="post" action="/sale/sales" cssClass="form-group kt-form">
                    <form:input path="id" type="hidden"/>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content" data-ktwizard-state="current">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.firstName">Ad</form:label>
                                                <form:input path="customer.person.firstName" cssClass="form-control" placeholder="Adı daxil edin Məs: Səbuhi"/>
                                                <form:errors path="customer.person.firstName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.lastName">Soyad</form:label>
                                                <form:input path="customer.person.lastName" cssClass="form-control" placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                                <form:errors path="customer.person.lastName" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.fatherName">Ata adı</form:label>
                                                <form:input path="customer.person.fatherName" cssClass="form-control" placeholder="Ata adını daxil edin"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.birthday">Doğum tarixi</form:label>
                                                <div class="input-group date" >
                                                    <form:input path="customer.person.birthday" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.birthday" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                                <form:input path="customer.person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                                <form:errors path="customer.person.idCardSerialNumber" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                                <form:input path="customer.person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                                <form:errors path="customer.person.idCardPinCode" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin ön hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file1" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file1">Ş.v-nin ön hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label>Ş.v-nin arxa hissəsi</label>
                                                <div></div>
                                                <div class="custom-file">
                                                    <input type="file" name="file" class="custom-file-input" id="file2" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                                    <label class="custom-file-label" for="file2">Ş.v-nin arxa hissəsi</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.mobilePhone">Mobil nömrə</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.mobilePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.homePhone" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.email">Email</form:label>
                                                <div class="input-group" >
                                                    <form:input path="customer.person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                                    <div class="input-group-append">
                                                            <span class="input-group-text">
                                                                <i class="la la-at"></i>
                                                            </span>
                                                    </div>
                                                </div>
                                                <form:errors path="customer.person.contact.email" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.city">Şəhər</form:label>
                                                <form:select  path="customer.person.contact.city" cssClass="custom-select form-control">
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.address">Ünvan</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.address" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingCity">Yaşadığı şəhər</form:label>
                                                <form:select  path="customer.person.contact.livingCity" cssClass="custom-select form-control">
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id" />
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="form-group">
                                                <form:label path="customer.person.contact.livingAddress">Yaşayış ünvanı</form:label>
                                                <div class="input-group" >
                                                    <div class="input-group-append">
                                                        <span class="input-group-text">
                                                            <i class="la la-street-view"></i>
                                                        </span>
                                                    </div>
                                                    <form:input path="customer.person.contact.livingAddress" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="customer.person.contact.livingAddress" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-sm-8 offset-sm-2">
                                            <form:hidden path="action" cssClass="form-control"/>
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text" style="background-color: white; border-right: none;"><i class="la la-search"></i></span>
                                                    </div>
                                                    <form:input path="action.inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." style="border-left: none;" />
                                                    <div class="input-group-append">
                                                        <button class="btn btn-primary" type="button" onclick="findInventory($('input[name=\'action.inventory.barcode\']'))">İnventar axtar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="action.inventory.name">İnventar</form:label>
                                                <form:input path="action.inventory.name" cssClass="form-control" readonly="true"/>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <form:label path="action.warehouse.name">Anbar</form:label>
                                                <form:input path="action.warehouse.name" cssClass="form-control" readonly="true"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <form:label path="action.inventory.description">Açıqlama</form:label>
                                        <form:textarea path="action.inventory.description" cssClass="form-control" readonly="true"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__form">
                                    <div class="row">
                                        <div class="col-sm-4 offset-sm-3">
                                            <form:label path="payment.price">Qiymət</form:label>
                                            <form:select  path="payment.price" onchange="calculate($(this))" cssClass="custom-select form-control">
                                                <form:options items="${sale_prices}" itemLabel="name" itemValue="attr1" />
                                            </form:select>
                                            <form:errors path="payment.price" cssClass="control-label alert-danger"/>
                                        </div>
                                        <div class="col-sm-3 text-center">
                                            <div class="form-group mt-3 pt-4">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="payment.cash" onclick="doCash($(this), '10%')"/> Ödəniş nağd dırmı?
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row kt-hidden animated zoomIn" id="cash-div">
                                        <div class="col-md-5 offset-md-1">
                                            <div class="form-group">
                                                <form:label path="payment.discount">Endirim dəyəri</form:label>
                                                <form:input path="payment.discount" cssClass="form-control" readonly="true" cssStyle="text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;"/>
                                                <form:errors path="payment.discount" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <form:label path="payment.description">Açıqlama</form:label>
                                                <form:input path="payment.description" cssClass="form-control" readonly="true"/>
                                                <form:errors path="payment.description" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-10 offset-md-1">
                                            <div class="alert alert-info alert-elevate" role="alert" style="padding: 0; padding-left: 1rem; padding-right: 1rem; padding-top: 5px;">
                                                <div class="alert-icon"><i class="flaticon-warning kt-font-brand kt-font-light"></i></div>
                                                <div class="alert-text text-center">
                                                    <div style="font-size: 18px; font-weight: bold; letter-spacing: 2px;">
                                                        Yekun ödəniləcək məbləğ:
                                                        <span id="lastPriceLabel">0</span>
                                                        <span> AZN</span>
                                                        <form:hidden path="payment.lastPrice"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row animated zoomIn" id="credit-div">
                                        <div class="col-sm-7 offset-sm-1">
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.down">İlkin ödəniş</form:label>
                                                        <div class="input-group" >
                                                            <form:input path="payment.down" cssClass="form-control" placeholder="İlkin ödənişi daxil edin"/>
                                                            <div class="input-group-append">
                                                    <span class="input-group-text">
                                                        <i class="la la-usd"></i>
                                                    </span>
                                                            </div>
                                                        </div>
                                                        <form:errors path="payment.down" cssClass="alert-danger control-label"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.schedule">Ödəniş qrafiki</form:label>
                                                        <form:select  path="payment.schedule" cssClass="custom-select form-control">
                                                            <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                                        </form:select>
                                                        <form:errors path="payment.schedule" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <form:label path="payment.period">Ödəniş edilsin</form:label>
                                                        <form:select  path="payment.period" cssClass="custom-select form-control">
                                                            <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                                        </form:select>
                                                        <form:errors path="payment.period" cssClass="control-label alert-danger"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 text-center">
                                            <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 16px;padding-left: 7px; padding-right: 8px;" onclick="schedule($('input[name=\'payment.lastPrice\']'), $('input[name=\'payment.down\']'), $('select[name=\'payment.schedule\']'), $('select[name=\'payment.period\']'))"><i class="fa fa-play"></i> Ödəniş qrafiki yarat</button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-10 offset-sm-1" id="schedule-div">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Konsul
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Ven lider
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Satıcı
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="kt-separator kt-separator--border-dashed kt-separator--space-sm kt-separator--portlet-fit" style="margin: 1rem 0"></div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="row">
                                            <div class="col-md-3 offset-md-1 text-right">
                                                <div class="kt-wizard-v1__review-title p-2">
                                                    Canavasser
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <input type="text" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-wizard-v1__content" data-ktwizard-type="step-content">
                            <div class="kt-heading kt-heading--md">Review your Details and Submit</div>
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v1__review">
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Current Address
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Address Line 1<br />
                                            Address Line 2<br />
                                            Melbourne 3000, VIC, Australia
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Details
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Package: Complete Workstation (Monitor, Computer, Keyboard & Mouse)<br />
                                            Weight: 25kg<br />
                                            Dimensions: 110cm (w) x 90cm (h) x 150cm (L)
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Service Type
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Overnight Delivery with Regular Packaging<br />
                                            Preferred Morning (8:00AM - 11:00AM) Delivery
                                        </div>
                                    </div>
                                    <div class="kt-wizard-v1__review-item">
                                        <div class="kt-wizard-v1__review-title">
                                            Delivery Address
                                        </div>
                                        <div class="kt-wizard-v1__review-content">
                                            Address Line 1<br />
                                            Address Line 2<br />
                                            Preston 3072, VIC, Australia
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="kt-form__actions">
                            <button class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-prev">
                                Geri
                            </button>
                            <button class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-submit">
                                Yadda saxla
                            </button>
                            <button class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u" data-ktwizard-type="action-next">
                                İrəli
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    function schedule(lastPrice, down, schedule, period){
        var table='';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/payment/schedule/' + $(lastPrice).val() + '/' + $(down).val() + '/' + $(schedule).val() + '/' + $(period).val(),
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        table+="<table class='table table-striped- table-bordered table-hover table-checkable  animated zoomIn' id='schedule-table'><thead><th style='width: 20px;' class='text-center'>№</th><th>Ödəniş tarixi</th><th>Ödəniş məbləği</th></thead>"
                    },
                    success: function(data) {
                        table+="<tbody>";
                        $.each(data, function(k, v) {
                            table+="<tr><th>"+(parseInt(k)+1)+"</th><th>"+v.scheduleDate.split(' ')[0]+"</th><th>"+v.amount+" <i style='font-style: italic; font-size: 10px;'>AZN<i></th></tr>";
                            console.log(k + ' - ' + v)
                        });
                        table+="</tbody>";
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Əlaqə saxlamağınızı xahiş edirik.",
                            type: "error",
                            cancelButtonText: 'Bağla',
                            cancelButtonColor: '#c40000',
                            cancelButtonClass: 'btn btn-danger',
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        });
                    },
                    complete: function(){
                        table+="</table>";
                        $("#schedule-div").html(table);
                        $("#schedule-table").DataTable();
                    }
                })
            }
        });

    }
    function calculate(element){
        var price = $(element).val();
        var discount = $("input[name='payment.discount']").val();
        if(discount.trim().length>0){
            var discounts = discount.trim().split("%")
            if(discounts.length>1){
                price = price-price*parseFloat(discounts[0])*0.01;
            } else {
                price = price-parseFloat(discounts[0]);
            }
            price = Math.ceil(price);
        }
        $("#lastPriceLabel").text(price);
        $("input[name='payment.lastPrice']").val(price);
    }

    $(function(){
        $("select[name='payment.price']").change();
    })

    function doCash(element, defaultCash){
        if($(element).is(":checked")){
            $("#cash-div").removeClass("kt-hidden");
            $("#credit-div").addClass("kt-hidden");
            $("#schedule-div").html('');
            swal.fire({
                title: 'Endirimi təsdiq edirsinizmi?',
                html: 'Endirim faiz və ya məbləğini daxil edin',
                type: 'question',
                allowEnterKey: true,
                showCancelButton: true,
                buttonsStyling: false,
                cancelButtonText: 'Xeyr, edilməsin!',
                cancelButtonClass: 'btn btn-danger',
                confirmButtonText: 'Bəli, edilsin!',
                confirmButtonClass: 'btn btn-success',
                reverseButtons: true,
                allowOutsideClick: false,
                input: 'text',
                inputPlaceholder: 'Buraya daxil edin...',
                inputValue: defaultCash,
                inputAttributes: {
                    maxlength: 10,
                    autocapitalize: 'off',
                    autocorrect: 'off',
                    id: 'sale-value',
                    style: 'text-align: -webkit-center; text-align: center; font-weight: bold; letter-spacing: 3px;'
                },
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            }).then(function(result) {
                $("input[name='payment.discount']").val('');
                $("input[name='payment.description']").val('');
                if (result.value) {
                    $("input[name='payment.discount']").val($('#sale-value').val());
                    $("select[name='payment.price']").change();
                    if($('#sale-value').val()!==defaultCash){
                        swal.fire({
                            title: $('#sale-value').val()+' - Səbəbini daxil edin',
                            type: 'info',
                            allowEnterKey: true,
                            buttonsStyling: false,
                            confirmButtonText: 'Təsdiq edirəm',
                            confirmButtonClass: 'btn btn-default',
                            allowOutsideClick: false,
                            input: 'textarea',
                            inputPlaceholder: 'Buraya daxil edin...',
                            inputAttributes: {
                                autocapitalize: 'off',
                                autocorrect: 'off',
                                style: 'letter-spacing: 1px;',
                                'aria-label': 'Type your message here'
                            },
                            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                        }).then(function(result2){
                            if(result2.value.length>0){
                                $("input[name='payment.description']").val(result2.value);
                            }
                        })

                    }
                }
            })

        } else {
            $("#cash-div").addClass("kt-hidden");
            $("#credit-div").removeClass("kt-hidden");
        }
    }


    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    var KTWizard1 = function () {
        var wizardEl;
        var formEl;
        var validator;
        var wizard;

        var initWizard = function () {
            wizard = new KTWizard('kt_wizard_v1', {
                startStep: 1
            });

            wizard.on('beforeNext', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();
                }
            });

            wizard.on('beforePrev', function(wizardObj) {
                if (validator.form() !== true) {
                    wizardObj.stop();
                }
            });

            wizard.on('change', function(wizard) {
                setTimeout(function() {
                    KTUtil.scrollTop();
                }, 500);
            });
        }

        var initValidation = function() {
            validator = formEl.validate({
                ignore: ":hidden",
                rules: {
                    /*'customer.person.firstName': {
                        required: true
                    },
                    'customer.person.lastName': {
                        required: true
                    },
                    'customer.person.contact.mobilePhone': {
                        required: true
                    },
                    'customer.person.contact.address': {
                        required: true
                    },
                    'action.inventory.barcode': {
                        required: true
                    },
                    'action.inventory.name': {
                        required: true
                    },
                    'action.warehouse.name': {
                        required: true
                    },
                    'payment.price': {
                        required: true
                    },
                    height: {
                        required: true
                    },
                    length: {
                        required: true
                    },

                    delivery: {
                        required: true
                    },
                    packaging: {
                        required: true
                    },
                    preferreddelivery: {
                        required: true
                    },

                    locaddress1: {
                        required: true
                    },
                    locpostcode: {
                        required: true
                    },
                    loccity: {
                        required: true
                    },
                    locstate: {
                        required: true
                    },
                    loccountry: {
                        required: true
                    }*/
                },
                invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();

                    swal.fire({
                        "title": "",
                        "text": "Məlumatı daxil edin!",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                },
                submitHandler: function (form) {

                }
            });
        }

        var initSubmit = function() {
            var btn = formEl.find('[data-ktwizard-type="action-submit"]');

            btn.on('click', function(e) {
                e.preventDefault();

                if (validator.form()) {
                    KTApp.progress(btn);
                    formEl.ajaxSubmit({
                        success: function() {
                            KTApp.unprogress(btn);
                            swal.fire({
                                "title": "",
                                "text": "The application has been successfully submitted!",
                                "type": "success",
                                "confirmButtonClass": "btn btn-secondary"
                            });
                        }
                    });
                }
            });
        }

        return {
            init: function() {
                wizardEl = KTUtil.get('kt_wizard_v1');
                formEl = $('#kt_form');
                initWizard();
                initValidation();
                initSubmit();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTWizard1.init();
    });

    function findInventory(element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/warehouse/inventory/action/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $("input[name='action']").val('');
                            $("input[name='action.inventory.name']").val('');
                            $("textarea[name='action.inventory.description']").val('');
                            $("input[name='action.warehouse.name']").val('');
                        },
                        success: function(action) {
                            console.log(action);
                            $("input[name='action']").val(action.id);
                            $("input[name='action.inventory.name']").val(action.inventory.name);
                            $("textarea[name='action.inventory.description']").val(action.inventory.description);
                            $("input[name='action.warehouse.name']").val(action.warehouse.name);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "Əlaqə saxlamağınızı xahiş edirik.",
                                type: "error",
                                cancelButtonText: 'Bağla',
                                cancelButtonColor: '#c40000',
                                cancelButtonClass: 'btn btn-danger',
                                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                            });
                        }
                    })
                }
            });
        }
    }

    $('#modal-operation').on('shown.bs.modal', function() {
        $(document).off('focusin.modal');
    });

    var KTDatatablesBasicBasic = function() {

        var initTable1 = function() {
            var table = $('#schedule-table');

            table.DataTable({
                responsive: true,

                // DOM Layout settings
                dom: "<'row'<'col-sm-12'tr>><'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",

                lengthMenu: [5, 10, 25, 50, 75, 100, 200],

                pageLength: 25,

                language: {
                    'lengthMenu': 'Display _MENU_',
                },
                order: [[1, 'desc']],
                columnDefs: [
                    {
                        targets: 0,
                        width: '25px',
                        className: 'dt-center',
                        orderable: false
                    },
                ],
            });

            table.on('change', '.kt-group-checkable', function() {
                var set = $(this).closest('table').find('td:first-child .kt-checkable');
                var checked = $(this).is(':checked');

                $(set).each(function() {
                    if (checked) {
                        $(this).prop('checked', true);
                        $(this).closest('tr').addClass('active');
                    }
                    else {
                        $(this).prop('checked', false);
                        $(this).closest('tr').removeClass('active');
                    }
                });
            });

            table.on('change', 'tbody tr .kt-checkbox', function() {
                $(this).parents('tr').toggleClass('active');
            });
        };

        return {
            init: function() {
                initTable1();
            },

        };

    }();

</script>