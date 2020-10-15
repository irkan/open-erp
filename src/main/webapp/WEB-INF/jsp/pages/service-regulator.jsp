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
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'sales', 'delete')}"/>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/service-regulator/filter">
                            <form:hidden path="sales.organization.id" />
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
                                                <form:label path="sales.id">Satış</form:label>
                                                <form:input path="sales.id" cssClass="form-control" placeholder="Satış kodu" />
                                                <form:errors path="sales.id" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.customer.id">Müştəri kodu</form:label>
                                                <form:input path="sales.customer.id" cssClass="form-control" placeholder="#######" />
                                                <form:errors path="sales.customer.id" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="servicedDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="servicedDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="servicedDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="servicedDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="servicedDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="servicedDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="serviceNotification.id">Filter</form:label>
                                                <form:select path="serviceNotification.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${service_notifications}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.customer.person.firstName">Müştərinin adı</form:label>
                                                <form:input path="sales.customer.person.firstName" cssClass="form-control" placeholder="Müştərinin adı" />
                                                <form:errors path="sales.customer.person.firstName" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sales.customer.person.lastName">Müştərinin soyadı</form:label>
                                                <form:input path="sales.customer.person.lastName" cssClass="form-control" placeholder="Müştərinin soyadı" />
                                                <form:errors path="sales.customer.person.lastName" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="padding-top: 30px;">
                                            <div class="form-group">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="sales.notServiceNext"/> Bir daha narahat edilməsin
                                                    <span></span>
                                                </label>
                                            </div>
                                        </div>
                                        <c:if test="${delete.status}">
                                            <div class="col-md-2" style="padding-top: 30px;">
                                                <div class="form-group">
                                                    <label class="kt-checkbox kt-checkbox--brand">
                                                        <form:checkbox path="sales.active"/> Satış aktual məlumat
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
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="view1" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'sales', 'view')}"/>
                            <c:set var="view2" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'customer', 'view')}"/>
                            <c:set var="view3" value="${utl:checkOperation(sessionScope.user.userModuleOperations, 'contact-history', 'view')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th style="min-width: 40px;"></th>
                                    <th>ID #</th>
                                    <th style="min-width: 70px;">Satış nömrəsi</th>
                                    <th>Müştəri</th>
                                    <th>Müştəri ilə əlaqə</th>
                                    <th>Xəbərdarlıq tarixi</th>
                                    <th>Filter</th>
                                    <th>Satış</th>
                                    <th>Sonuncu qeyd</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />">
                                        <td></td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td>
                                            <c:if test="${not empty t.sales.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.sales.id}" />', 'Satış kodu <b><c:out value="${t.sales.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${view1.status}">
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.sales.id}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="min-width: 220px;">
                                            <c:if test="${not empty t.sales.customer.id}">
                                                <a href="javascript:copyToClipboard2('<c:out value="${t.sales.customer.id}" />', 'Müştəri kodu <b><c:out value="${t.sales.customer.id}" /></b> kopyalandı')" class="kt-font-lg kt-font-bold kt-font-info kt-font-hover-danger pl-2 pr-2"><i class="la la-copy"></i></a>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${view2.status}">
                                                    <a href="javascript:window.open('/crm/customer/<c:out value="${t.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.person.fullName}"/></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${t.sales.customer.person.fullName}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div>
                                                <a href="#" class="kt-link kt-font-bolder kt-font-danger"><c:out value="${t.sales.customer.person.contact.mobilePhone}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder kt-font-warning"><c:out value="${t.sales.customer.person.contact.homePhone}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.person.contact.relationalPhoneNumber1}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.person.contact.relationalPhoneNumber2}"/></a>
                                                <a href="#" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.person.contact.relationalPhoneNumber3}"/></a>
                                            </div>
                                            <div>
                                                <c:out value="${t.sales.customer.person.contact.city.name}"/>
                                                <c:out value="${t.sales.customer.person.contact.address}"/>
                                            </div>
                                        </td>
                                        <td>
                                            <fmt:formatDate value = "${t.servicedDate}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            <c:out value="${t.serviceNotification.name}"/>
                                        </td>
                                        <td>
                                            <c:out value="${t.sales.id}" />
                                        </td>
                                        <td style="max-width: 300px">
                                            <c:if test="${view3.status}">
                                                <c:if test="${t.sales.contactHistories.size()>0}">
                                                    <a href="javascript:window.open('/collect/contact-history/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder">
                                                        <c:set var="ch" value="${t.sales.contactHistories.get(t.sales.contactHistories.size()-1)}"/>
                                                        <c:out value="${ch.user.username}"/>
                                                        <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                        <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                        <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                        <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                    </a>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${!view3.status}">
                                                <c:if test="${t.sales.contactHistories.size()>0}">
                                                    <c:set var="ch" value="${t.sales.contactHistories.get(t.sales.contactHistories.size()-1)}"/>
                                                    <c:out value="${ch.user.username}"/>
                                                    <fmt:formatDate value = "${ch.createdDate}" pattern = "dd.MM.yyyy" /> -
                                                    <fmt:formatDate value = "${ch.nextContactDate}" pattern = "dd.MM.yyyy" /> -
                                                    <c:out value="${fn:substring(ch.description, 0, 94)}" />
                                                    <c:out value="${ch.description.length()>94?' . . . ':''}"/>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                <a href="javascript:serviceRegulator('view', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                    <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${transfer.status}">
                                                <a href="javascript:transfer($('#transfer-form'), '<c:out value="${t.id}" />', '<c:out value="${t.servicedDate}" />', 'transfer-modal-operation', '<c:out value="${transfer.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                    <i class="la <c:out value="${transfer.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:serviceRegulator('edit', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                    <i class="la <c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.serviceNotification.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="la <c:out value="${delete.object.icon}"/>"></i>
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

<div class="modal fade" id="modal-operation" tabindex="100" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/sale/service-regulator" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="sales.id">Satış kodu</form:label>
                        <form:input path="sales.id" cssClass="form-control" placeholder="Satış kodu" />
                        <form:errors path="sales.id" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="serviceNotification.id">Filter</form:label>
                        <form:select path="serviceNotification.id" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${service_notifications}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="servicedDate">Xəbərdarlıq tarixi</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="servicedDate" autocomplete="off" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="servicedDate" cssClass="control-label alert-danger"/>
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

<div class="modal fade" id="transfer-modal-operation" tabindex="100" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="transfer-form" method="post" action="/sale/service-regulator/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="ids"/>
                    <form:hidden path="servicedDate"/>
                    <div class="form-group text-center">
                        <label class="kt-form__label" id="label-description"></label>
                    </div>
                    <div class="form-group">
                        <form:label path="postpone.id">Ertələmə</form:label>
                        <select name="postpone.id" class="custom-select form-control" onchange="postpone($(this), $('#transfer-form').find('input[name=servicedDate]'))">
                            <option value=""></option>
                            <c:forEach var="t" items="${postpones}" varStatus="loop">
                                <option value="<c:out value="${t.id}"/>" attr1="<c:out value="${t.attr1}"/>" attr2="<c:out value="${t.attr2}"/>">
                                    <c:out value="${t.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group kt-hide" id="postponeDateGroup">
                        <form:label path="postponeDate">Ertələmə tarixi</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="postponeDate" autocomplete="off" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="postponeDate" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group notServiceNextReason kt-hide">
                        <form:label path="sales.notServiceNextReason">Səbəb</form:label>
                        <form:textarea path="sales.notServiceNextReason" cssClass="form-control" placeholder="Səbəbi daxil edin"/>
                        <form:errors path="sales.notServiceNextReason" cssClass="alert alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" id="transfer-modal-save" class="btn btn-primary" onclick="submit($('#transfer-form'));">Servis yarat</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    $("#datatable").DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
               $.extend( true, {}, buttonCommon, {
                    extend: 'copyHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'csvHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'excelHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'pdfHtml5'
                } ),
                $.extend( true, {}, buttonCommon, {
                    extend: 'print'
                } )
        ],
        </c:if>
        responsive: true,
        fixedHeader: {
           headerOffset: $('#kt_header').outerHeight()
        },
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '30px',
                className: 'text-center pt-3',
                orderable: false,
                render: function(data, type, full, meta) {
                    return `
                        <label class="checkbox checkbox-single" style="margin-bottom: 0;">
                            <input type="checkbox" value="`+full[1]+`" label="`+full[6]+`"  sales="`+full[7]+`"  class="checkable" style="width: 20px; height: 20px;"/>
                            <span></span>
                        </label>`;
                },
                selectRow: true
            }
        ],
        select: {
            style:    'multi',
            selector: 'td:first-child'
        }
    }).on("click", "input[type='checkbox']", function(){
        var sales = $(this).attr('sales');
        var status = false;
        $("input.checkable:checked").each(function() {
            if(sales!==$(this).attr('sales')){
                status = true;
            }
        });
        if(status){
            $("input.checkable:checked").prop('checked', false);
            $(this).prop('checked', true);
        }
    });

    $( "#form" ).validate({
        rules: {
            "sales.id": {
                required: true,
                digits: true
            },
            "serviceNotification.id": {
                required: true
            },
            servicedDate: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    <c:if test="${view.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        serviceRegulator('view', $('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
    });
    </c:if>

    $( "#transfer-form" ).validate({
        rules: {
            description: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    function postpone(element, date){
        var dt = new Date($(date).val().replace( /(\d{2}).(\d{2}).(\d{4})/, "$2/$1/$3"));
        //dt.setDate()
        console.log(date);
        console.log(dt);

        $("#transfer-form").find("input[name='postponeDate']").val('');
        if($(element).val()===""){
            $(".notServiceNextReason").addClass("kt-hide");
            $("#postponeDateGroup").addClass("kt-hide");
            $("#transfer-modal-save").text("Servis yarat");
        } else {
            $("#postponeDateGroup").removeClass("kt-hide");
            $(".notServiceNextReason").removeClass("kt-hide");
            $("#transfer-modal-save").text("Ertələ");
        }
    }

    function transfer(form, id, date, modal, modal_title){
        $(form).find("input[name='id']").val(id);
        $(form).find("input[name='servicedDate']").val(date);
        var ids="";
        var labels="";
        $("#datatable").find("input[type=checkbox]:checked").map(function() {
            ids+=$(this).val()+",";
            labels+=$(this).attr('label')+"  ";
        });
        if(ids.length>0){
            $(form).find("input[name='ids']").val(ids);
            $(form).find("#label-description").text(labels);
        }
        $('#' + modal).find(".modal-title").html(modal_title);
        $('#' + modal).modal('toggle');
    }

    function serviceRegulator(oper, form, dataId, modal, modal_title){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/sale/api/service-regulator/'+dataId,
                    type: 'GET',
                    dataType: 'text',
                    beforeSend: function() {

                    },
                    success: function(data) {
                        setSales($('#form'), data);
                        if(oper==="view"){
                            view(form, data, modal, modal_title)
                        } else if(oper==="edit"){
                            edit(form, data, modal, modal_title)
                        }
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta",
                            html: "Xəta baş verdi!",
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

    function setSales(form, data){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log('obj-------------------------');
        console.log(obj);
       $(form).find("input[name='sales.id']").val(obj.sales.id);
    }

</script>