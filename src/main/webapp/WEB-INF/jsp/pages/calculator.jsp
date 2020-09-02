<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
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
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <form:form modelAttribute="form" id="form" cssClass="form-group kt-form">
                    <div class="kt-portlet__body">
                        <div class="row">
                            <div class="col-sm-3 offset-sm-3">
                                <%--<form:label path="payment.price">Qiymət</form:label>
                                <form:select  path="payment.price" onchange="calculate($(this))" cssClass="custom-select form-control">
                                    <form:options items="${sale_prices}" itemLabel="name" itemValue="attr1" />
                                </form:select>
                                <form:errors path="payment.price" cssClass="control-label alert-danger"/>--%>

                                <form:label path="payment.price">Qiymət</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payment.price" onchange="calculate($('#form'), $(this), 'lastPriceLabel')" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                </div>
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
                                    <div class="col-sm-3">
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
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <form:label path="payment.schedule">Ödəniş qrafiki</form:label>
                                            <form:select  path="payment.schedule" cssClass="custom-select form-control">
                                                <form:options items="${payment_schedules}" itemLabel="name" itemValue="id" />
                                            </form:select>
                                            <form:errors path="payment.schedule" cssClass="control-label alert-danger"/>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <form:label path="payment.period">Ödəniş edilsin</form:label>
                                            <form:select  path="payment.period" cssClass="custom-select form-control">
                                                <form:options items="${payment_periods}" itemLabel="name" itemValue="id" />
                                            </form:select>
                                            <form:errors path="payment.period" cssClass="control-label alert-danger"/>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="form-group">
                                            <form:label path="payment.gracePeriod">Güzəşt müddəti / AY</form:label>
                                            <div class="input-group" >
                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                                <form:input path="payment.gracePeriod" cssClass="form-control" placeholder="Daxil edin"/>
                                            </div>
                                            <form:errors path="payment.gracePeriod" cssClass="control-label alert-danger"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 text-center">
                                <button type="button" class="btn btn-outline-info btn-tallest" style="font-size: 16px;padding-left: 7px; padding-right: 8px;" onclick="schedule($('input[name=\'payment.lastPrice\']'), $('input[name=\'payment.down\']'), $('select[name=\'payment.schedule\']'), $('select[name=\'payment.period\']'), $('input[name=\'payment.gracePeriod\']'))"><i class="fa fa-play"></i> Ödəniş qrafiki yarat</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-10 offset-sm-1" id="schedule-div">
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
</div>

<script>

    $( "#form" ).validate({
        rules: {
            'payment.price': {
                required: true,
                number: true,
                pattern: /^(1499|1599|1699)$/
            },
            'payment.gracePeriod': {
                required: true,
                number: true,
                pattern: /^(0|1)$/
            },
            'payment.down': {
                required: true,
                number: true,
                min: 0
            },
            'payment.schedulePrice': {
                required: false,
                number: true,
                min: 0
            }
        },
        messages: {
            'payment.price': "1499, 1599 və ya 1699 ola bilər",
            'payment.gracePeriod': "0 və ya 1 (~30 gün) ay ola bilər",
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    function schedule(lastPrice, down, schedule, period, gracePeriod){
        var table='';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/payment/schedule/' + $(lastPrice).val() + '/' + $(down).val() + '/' + $(schedule).val() + '/' + $(period).val() + '/' + $(gracePeriod).val() + '/0',
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        table+="<table class='table table-striped- table-bordered table-hover table-checkable  animated zoomIn' id='schedule-table'><thead><th style='width: 20px;' class='text-center'>№</th><th>Ödəniş tarixi</th><th>Ödəniş məbləği</th></thead>"
                    },
                    success: function(data) {
                        table+="<tbody>";
                        $.each(data, function(k, v) {
                            table+="<tr>" +
                                "<th>"+(parseInt(k)+1)+"</th>" +
                                "<th>" +
                                "<input type='hidden' name='payment.schedules["+parseInt(k)+"].scheduleDate' /> "+
                                v.scheduleDate.split(' ')[0]+
                                "</th>" +
                                "<th>" +
                                "<input type='hidden' name='payment.schedules["+parseInt(k)+"].amount' />"+
                                v.amount+
                                " <i style='font-style: italic; font-size: 10px;'>AZN<i></th></tr>";
                            console.log(k + ' - ' + v)
                        });
                        table+="</tbody>";
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Məlumatları doğru daxil edin.",
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

    function calculate(form, element, lastPriceLabelId){
        var price = $(element).val();
        var discount = $(form).find("input[name='payment.discount']").val();
        if(discount.trim().length>0){
            var discounts = discount.trim().split("%");
            if(discounts.length>1){
                price = price-price*parseFloat(discounts[0])*0.01;
            } else {
                price = price-parseFloat(discounts[0]);
            }
            price = Math.ceil(price);
        }
        $("#"+lastPriceLabelId).text(price);
        $(form).find("input[name='payment.lastPrice']").val(price);
    }

    $(function(){
        $("select[name='payment.price']").change();
    });

    $("input[name='payment.price']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.gracePeriod']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.down']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.schedulePrice']").inputmask('decimal', {
        rightAlignNumerics: false
    });

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
</script>




