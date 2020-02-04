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
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                                <c:out value="${list.totalElements>0?list.totalElements:0} sətr"/>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/sales/filter">
                            <form:hidden path="organization" />
                            <form:hidden path="service" />
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="customer">Müştəri</form:label>
                                                <form:input path="customer" cssClass="form-control" placeholder="Müştəri kodu" />
                                                <form:errors path="customer" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="saleDateFrom">Satış tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="saleDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="saleDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="saleDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="saleDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="saleDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="guaranteeExpireFrom">Qarantiya bitir - tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="guaranteeExpireFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="guaranteeExpireFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="guaranteeExpire">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="guaranteeExpire" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="guaranteeExpire" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.lastPriceFrom">Qiymətdən</form:label>
                                                <form:input path="payment.lastPriceFrom" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="payment.lastPriceFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payment.lastPrice">Qiymətədək</form:label>
                                                <form:input path="payment.lastPrice" cssClass="form-control" placeholder="Qiyməti daxil edin"/>
                                                <form:errors path="payment.lastPrice" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <c:if test="${delete.status}">
                                            <div class="col-md-2" style="padding-top: 30px;">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="active"/> Aktual məlumat
                                                        <span></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-md-1 text-right">
                                    <div class="form-group">
                                        <a href="#" onclick="location.reload();" class="btn btn-danger btn-elevate btn-icon-sm btn-block mb-2" style="padding: 0.35rem 0.6rem;">
                                            <i class="la la-trash"></i> Sil
                                        </a>
                                        <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2" style="padding: 0.35rem 0.6rem">
                                            <i class="la la-search"></i> Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>Kod</th>
                                    <th>İnventar</th>
                                    <th>Satış tarixi</th>
                                    <th>Müştəri</th>
                                    <th>Qiymət</th>
                                    <th>Qarantiya</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td style="<c:out value="${t.payment.cash?'background-color: #e6ffe7 !important':'background-color: #ffeaf1 !important'}"/>"><c:out value="${t.id}" /></td>
                                        <th>
                                            <c:forEach var="p" items="${t.salesInventories}" varStatus="lp">
                                                <c:out value="${lp.index+1}" />.
                                                <c:out value="${p.inventory.name}" /><br/>
                                                <c:out value="${p.inventory.barcode}" /><br/>
                                                <c:out value="${p.inventory.description}" />
                                            </c:forEach>
                                        </th>
                                        <td><fmt:formatDate value = "${t.saleDate}" pattern = "dd.MM.yyyy" /></td>
                                        <th>
                                            <a href="javascript:window.open('/crm/customer/<c:out value="${t.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.customer.id}" />: <c:out value="${t.customer.person.fullName}"/></a>
                                        </th>
                                        <th>
                                            Qiymət: <c:out value="${t.payment.price}" /><br/>
                                            <c:if test="${not empty t.payment.discount}">
                                                Endirim: <c:out value="${t.payment.discount}" /><br/>
                                            </c:if>
                                            <c:if test="${not empty t.payment.description}">
                                                Səbəbi: <c:out value="${t.payment.description}" /><br/>
                                            </c:if>
                                            <c:if test="${t.payment.down>0}">
                                                İlkin ödəniş: <c:out value="${t.payment.down}" /><br/>
                                            </c:if>
                                            Son qiymət: <c:out value="${t.payment.lastPrice}" />
                                        </th>
                                        <th>
                                            <fmt:formatDate value = "${t.guaranteeExpire}" pattern = "dd.MM.yyyy" />, <c:out value="${t.guarantee}" /> ay
                                        </th>
                                        <td nowrap class="text-center">
                                            <c:if test="${approve.status and !t.approve}">
                                                <a href="javascript:approveData($('#approve-form'),'<c:out value="${t.id}" /> nömrəli müqavilənin təsdiqi', '<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                                                    <i class="<c:out value="${approve.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');getInventories('<c:out value="${t.id}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.id}" /> <br/> <c:out value="${t.customer.person.fullName}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="<c:out value="${delete.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
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
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/sales" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization"/>
                    <form:hidden path="active"/>
                    <form:hidden path="service"/>
                    <form:hidden path="payment.cash" value="1"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="saleDate">Satış tarixi</form:label>
                                <div class="input-group date" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="saleDate" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                                </div>
                                <form:errors path="saleDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-8">

                            <div class="form-group">
                                <form:label path="payment.description">Açıqlama</form:label>
                                <form:input path="payment.description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                <form:errors path="payment.description" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="customer">Müştəri</form:label>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="input-group">
                                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-user"></i></span></div>
                                            <form:input path="customer" autocomplete="false" class="form-control" placeholder="M.kodu..."/>
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <a href="javascript:findCustomer($('#form').find('input[name=\'customer\']'));" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                            Axtar
                                        </a>
                                    </div>
                                    <div class="col-3">
                                        <a href="javascript:window.open('/crm/customer', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn btn-bold btn-sm btn-label-primary">
                                            Müştəri
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <table id="customer-content" class="table table-striped- table-bordered table-hover table-checkable"></table>
                        </div>
                        <div class="col-md-5">
                            <div id="kt_repeater_1">
                                <div class="form-group form-group-last row" id="kt_repeater_2">
                                    <div data-repeater-list="" class="col-lg-12" id="data-repeater-list">
                                        <div data-repeater-item class="form-group row align-items-center">
                                            <div class="col-9">
                                                <div class="kt-form__group--inline">
                                                    <div class="kt-form__label">
                                                        <label>İnventar:</label>
                                                    </div>
                                                    <div class="kt-form__control">
                                                        <input type="text" attr="barcode" name="inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))">
                                                        <label attr="name" name="inventory.name"></label>
                                                        <input type="hidden" attr="id" name="inventory" class="form-control" placeholder="Barkodu daxil edin..." >
                                                    </div>
                                                </div>
                                                <div class="d-md-none kt-margin-b-10"></div>
                                            </div>
                                            <div class="col-3">
                                                <div>
                                                    <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">
                                                        <i class="la la-trash-o"></i>
                                                        Sil
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group form-group-last row">
                                    <div class="col-lg-6">
                                        <a href="javascript:;" data-repeater-create="" class="btn btn-bold btn-sm btn-label-primary">
                                            <i class="la la-plus"></i> Əlavə et
                                        </a>
                                    </div>
                                    <div class="col-lg-6 text-right">
                                        <a href="javascript:window.open('/warehouse/inventory', 'mywindow', 'width=1250, height=800')" data-repeater-delete="" class="btn-sm btn btn-label-success btn-bold">
                                            <i class="la la-search"></i>
                                            Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="payment.price">Qiymət</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payment.price" cssClass="form-control" placeholder="Qiyməti daxil edin" onchange="calculate($('input[name=\"payment.price\"]').val(), $('input[name=\"payment.discount\"]').val())" onkeyup="calculate($('input[name=\"payment.price\"]').val(), $('input[name=\"payment.discount\"]').val())"/>
                                </div>
                                <form:errors path="payment.price" cssClass="alert-danger control-label"/>
                            </div>
                            <div class="form-group">
                                <form:label path="payment.discount">Endirim</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payment.discount" cssClass="form-control" placeholder="Endirim varsa daxil edin" onchange="calculate($('input[name=\"payment.price\"]').val(), $('input[name=\"payment.discount\"]').val())" onkeyup="calculate($('input[name=\"payment.price\"]').val(), $('input[name=\"payment.discount\"]').val())"/>
                                </div>
                                <form:errors path="payment.discount" cssClass="alert-danger control-label"/>
                            </div>
                            <div class="form-group">
                                <form:label path="payment.lastPrice">Son qiymət</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payment.lastPrice" cssClass="form-control" placeholder="Son qiyməti daxil edin" readonly="true"/>
                                </div>
                                <form:errors path="payment.lastPrice" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<form id="approve" method="post" action="/sale/sales/approve" style="display: none">
    <input type="hidden" name="id" />
</form>

<form id="approve-form" method="post" action="/sale/sales/approve" style="display: none">
    <input type="hidden" name="id" />
</form>

<script>
    $("#datatable").DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '50px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    $('.select2-single').select2({
        placeholder: "Əməkdaşı seçin",
        allowClear: true
    });

    function calculate(price, discount){
        console.log(price);
        if(price.trim().length===0){
            price = 0;
        }
        if(discount.trim().length===0){
            discount = 0;
        }
        console.log(discount);
        if(discount!==0 && discount.indexOf('%')!==-1){
            console.log(parseFloat(price)*parseFloat(discount.replace('%', '*0.01')));
            discount = parseFloat(price)*parseFloat(discount.replace('%', ''))/100;
        }
        var result = parseFloat(price)-parseFloat(discount)
        if(result<0){
            result=0;
        }
        $("input[name='payment.lastPrice']").val(result);
    }

    function findCustomer(element){
        var tr = '';
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/crm/api/customer/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                            $('#customer-content').html('');
                        },
                        success: function(customer) {
                            console.log(customer);
                            tr += '<tr class="text-center"><td class="text-center" colspan="2">'+customer.person.fullName+'</td></tr>';
                            tr += '<tr><td>Doğum tarixi</td><td>'+customer.person.birthday+'</td></tr>';
                            tr += '<tr><td>Seriya nömrəsi</td><td>'+customer.person.idCardSerialNumber+'</td></tr>';
                            tr += '<tr><td>Pin kodu</td><td>'+customer.person.idCardPinCode+'</td></tr>';
                            $('#customer-content').html(tr);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Müştəri tapılmadı!",
                                html: "Müştəri kodunun doğruluğunu yoxlayın.",
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

    function findInventory(element){
        if($(element).val().trim().length>0){
            swal.fire({
                text: 'Proses davam edir...',
                allowOutsideClick: false,
                onOpen: function() {
                    swal.showLoading();
                    $.ajax({
                        url: '/warehouse/api/inventory/'+$(element).val(),
                        type: 'GET',
                        dataType: 'json',
                        beforeSend: function() {
                        },
                        success: function(inventory) {
                            $(element).parent().find("input[attr='id']").val(inventory.id);
                            $(element).parent().find("label[attr='name']").text(inventory.name);
                            console.log(inventory);
                            swal.close();
                        },
                        error: function() {
                            swal.fire({
                                title: "Xəta baş verdi!",
                                html: "<c:out value="${sessionScope.user.employee.person.lastName}"/> <c:out value="${sessionScope.user.employee.person.firstName}"/> adına inventar təhkim edilməyib",
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

    function getInventories(salesId){
        var content = '';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/api/service/inventory/'+salesId,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $("#kt_repeater_1").find("#data-repeater-list").html(content);
                    },
                    success: function(data) {
                        console.log(data);

                        $.each(data, function( index, value ) {
                            content+='<div data-repeater-item="" class="form-group row align-items-center">\n' +
                                '                                            <div class="col-9">\n' +
                                '                                                <div class="kt-form__group--inline">\n' +
                                '                                                    <div class="kt-form__label">\n' +
                                '                                                        <label>İnventar:</label>\n' +
                                '                                                    </div>\n' +
                                '                                                    <div class="kt-form__control">\n' +
                                '                                                        <input type="text" attr="barcode" name="salesInventories['+index+'].inventory.barcode" class="form-control" placeholder="Barkodu daxil edin..." onchange="findInventory($(this))" value="'+value.inventory.barcode+'">\n' +
                                '                                                        <label attr="name" name="salesInventories['+index+'].inventory.name">'+value.inventory.name+'</label>\n' +
                                '                                                        <input type="hidden" attr="id" name="salesInventories['+index+'].inventory" class="form-control" placeholder="Barkodu daxil edin..." value="'+value.inventory.id+'">\n' +
                                '                                                    </div>\n' +
                                '                                                </div>\n' +
                                '                                                <div class="d-md-none kt-margin-b-10"></div>\n' +
                                '                                            </div>\n' +
                                '                                            <div class="col-3">\n' +
                                '                                                <div>\n' +
                                '                                                    <a href="javascript:;" data-repeater-delete="" class="btn-sm btn btn-label-danger btn-bold">\n' +
                                '                                                        <i class="la la-trash-o"></i>\n' +
                                '                                                        Sil\n' +
                                '                                                    </a>\n' +
                                '                                                </div>\n' +
                                '                                            </div>\n' +
                                '                                        </div>';
                        });

                        $("#kt_repeater_1").find("#data-repeater-list").html(content);
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "<c:out value="${sessionScope.user.employee.person.lastName}"/> <c:out value="${sessionScope.user.employee.person.firstName}"/> adına inventar təhkim edilməyib",
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

    $('#modal-operation').on('shown.bs.modal', function() {
        $(document).off('focusin.modal');
    });

    <c:if test="${edit.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

    var KTFormRepeater = function() {
        var demo1 = function() {
            $('#kt_repeater_1').repeater({
                initEmpty: false,

                defaultValues: {
                    'text-input': 'foo'
                },

                show: function () {
                    $(this).slideDown();
                    var elements = $($(this).parent()).find(".align-items-center");
                    var lastBefore = $(elements).eq(-2);
                    var barcode = $(lastBefore).find("input[attr='barcode']");
                    if(barcode.val().trim().length>0){
                        findInventory(barcode);
                    }
                },

                hide: function (deleteElement) {
                    swal.fire({
                        title: 'İnventarı ləğv etməyə əminsinizmi?',
                        type: 'info',
                        allowEnterKey: true,
                        showCancelButton: true,
                        buttonsStyling: false,
                        cancelButtonText: 'Xeyr, edilməsin!',
                        cancelButtonClass: 'btn btn-danger',
                        confirmButtonText: 'Bəli, edilsin!',
                        confirmButtonClass: 'btn btn-success',
                        reverseButtons: true,
                        allowOutsideClick: false,
                        footer: '<a href>Məlumatlar yenilənsinmi?</a>'
                    }).then(function(result){
                        if(result.value){
                            $(this).slideUp(deleteElement);
                        }
                    })
                }
            });
        };
        return {
            init: function() {
                demo1();
            }
        };
    }();

    jQuery(document).ready(function() {
        KTFormRepeater.init();
    });

    $( "#form" ).validate({
        rules: {
            customer: {
                required: true,
                digits: true
            },
            'payment.price': {
                required: true,
                number: true,
                min: 1
            },
            'payment.lastPrice': {
                required: true,
                number: true,
                min: 0
            },
            'payment.discount': {
                required: false,
                number: true,
                min: 1
            },
            'salesInventories[0].inventory.barcode': {
                required: true
            },
            'salesInventories[1].inventory.barcode': {
                required: true
            },
            'salesInventories[2].inventory.barcode': {
                required: true
            },
            'salesInventories[3].inventory.barcode': {
                required: true
            },
            'salesInventories[4].inventory.barcode': {
                required: true
            },
            'salesInventories[5].inventory.barcode': {
                required: true
            },
            'salesInventories[6].inventory.barcode': {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='payment.price']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    $("input[name='payment.discount']").inputmask('decimal', {
        rightAlignNumerics: false
    });
    $("input[name='payment.lastPrice']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    function approveData(form, info, id){
        swal.fire({
            title: 'Əminsinizmi?',
            html: info,
            type: 'success',
            allowEnterKey: true,
            showCancelButton: true,
            buttonsStyling: false,
            cancelButtonText: 'İmtina',
            cancelButtonColor: '#d1d5cf',
            cancelButtonClass: 'btn btn-default',
            confirmButtonText: 'Bəli, icra edilsin!',
            confirmButtonColor: '#c40000',
            confirmButtonClass: 'btn btn-success',
            footer: '<a href>Məlumatlar yenilənsinmi?</a>'
        }).then(function(result) {
            if (result.value) {
                swal.fire({
                    text: 'Proses davam edir...',
                    allowOutsideClick: false,
                    onOpen: function() {
                        $(form).find("input[name='id']").val(id);
                        swal.showLoading();
                        $(form).submit();
                    }
                })
            }
        })
    }
</script>