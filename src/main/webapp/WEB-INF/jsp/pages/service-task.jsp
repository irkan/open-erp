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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/sale/service-regulator/filter">
                            <form:hidden path="organization" />
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
                                                <form:label path="sales">Satış</form:label>
                                                <form:input path="sales" cssClass="form-control" placeholder="Satış kodu" />
                                                <form:errors path="sales" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="taskDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="taskDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="taskDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="taskDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="taskDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="taskDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin"/>
                                                <form:errors path="description" cssClass="alert alert-danger"/>
                                            </div>
                                        </div>
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
                            <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
                                <thead>
                                <tr>
                                    <th>Task #</th>
                                    <th>Satış nömrəsi</th>
                                    <th>Satış tarxi</th>
                                    <th>Son servis tarxi</th>
                                    <th>Tarix</th>
                                    <th>Müştəri</th>
                                    <th>Müştəri ilə əlaqə</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td><span class="kt-padding-5"><c:out value="${t.id}" /></span></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${t.sales.service}">
                                                    <a href="javascript:window.open('/sale/service/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript:window.open('/sale/sales/<c:out value="${t.sales.id}" />', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.id}" /></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value = "${t.sales.saleDate}" pattern = "dd, MMMM" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value = "${t.sales.saleDate}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            <fmt:formatDate value = "${t.taskDate}" pattern = "dd.MM.yyyy" />
                                        </td>
                                        <td>
                                            <a href="javascript:window.open('/crm/customer/<c:out value="${t.sales.customer.id}"/>', 'mywindow', 'width=1250, height=800')" class="kt-link kt-font-bolder"><c:out value="${t.sales.customer.id}" />: <c:out value="${t.sales.customer.person.fullName}"/></a>
                                        </td>
                                        <td>
                                            <c:out value="${t.sales.customer.person.contact.mobilePhone}"/>
                                            <c:out value="${t.sales.customer.person.contact.city.name}"/>
                                            <c:out value="${t.sales.customer.person.contact.address}"/>
                                        </td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${transfer.status}">
                                                <a href="javascript:edit($('#transfer-form'), '<c:out value="${utl:toJson(t)}" />', 'transfer-modal-operation', '<c:out value="${transfer.object.name}" />');check('<c:out value="${utl:toJson(t.serviceRegulatorTasks)}" />')" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                                                    <i class="la <c:out value="${transfer.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                    <i class="flaticon2-cancel"></i>
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
                <form:form modelAttribute="form" id="transfer-form" method="post" action="/collect/service-task/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="sales"/>
                    <form:hidden path="organization"/>
                    <div class="form-group text-center">
                        <form:label path="description" cssStyle="letter-spacing: 4px; font-weight: bold; font-size: 1.3rem;">Filterlər</form:label>
                        <div class="row text-left filters">
                        <c:forEach var="t" items="${service_notifications}" varStatus="loop">
                            <form:hidden path="serviceRegulatorTasks[${loop.index}].serviceRegulator.serviceNotification.attr2" value="${t.attr2}"/>
                            <div class="col-md-6">
                                <label class="kt-checkbox kt-checkbox--brand">
                                    <form:checkbox path="serviceRegulatorTasks[${loop.index}].serviceRegulator.serviceNotification.id" value="${t.id}"/> <c:out value="${t.name}"/>
                                    <span></span>
                                </label>
                            </div>
                        </c:forEach>
                        </div>
                        <form:errors path="description" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
                        <form:errors path="description" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <label class="kt-checkbox kt-checkbox--brand">
                            <form:checkbox path="sales.notServiceNext" onclick="reason($(this))"/> Bir daha narahat edilməsin
                            <span></span>
                        </label>
                    </div>
                    <div class="form-group notServiceNextReason kt-hide">
                        <form:label path="sales.notServiceNextReason">Səbəb</form:label>
                        <form:textarea path="sales.notServiceNextReason" cssClass="form-control" placeholder="Səbəbi daxil edin"/>
                        <form:errors path="sales.notServiceNextReason" cssClass="alert alert-danger"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submitTransferForm($('#transfer-form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>

<script>
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
    })

    function check(data){
        data = data.replace(/\&#034;/g, '"');
        var obj = jQuery.parseJSON(data);
        console.log(obj);
        $(".filters").find("input[type='checkbox']").prop('checked', false);
        $(".filters").find("input[type='checkbox']").parent().find("span").removeClass(":after");
        $.each( $(".filters").find("input[type='checkbox']"), function( key, element ) {
            $.each(obj, function(i, v){
                $(".filters").find($('input[type=checkbox][value="'+v.serviceRegulator.serviceNotification.id+'"]')).prop('checked', true);
                $(".filters").find($('input[type=checkbox][value="'+v.serviceRegulator.serviceNotification.id+'"]')).parent().find("span").addClass(":after");
            })
        })
    }

    function reason(element){
        if(!$(element).is(":checked")){
            $(".notServiceNextReason").addClass("kt-hide");
        } else {
            $(".notServiceNextReason").removeClass("kt-hide");
        }
    }

    function submitTransferForm(form){
        var checkboxes = $(".filters").find("input[type='checkbox']:checked");
        if($("input[name='sales.notServiceNext']").is(":checked") && checkboxes.length>0){
            swal.fire({
                title: 'Əminsinizmi?',
                html: "Seçilmiş filterlər var. Servis yaradılmağına əminsinizmi?",
                type: 'success',
                allowEnterKey: true,
                showCancelButton: true,
                buttonsStyling: false,
                cancelButtonText: 'İmtina',
                cancelButtonColor: '#d1d5cf',
                cancelButtonClass: 'btn btn-default',
                confirmButtonText: 'Bəli, razıyam!',
                confirmButtonColor: '#c40000',
                confirmButtonClass: 'btn btn-danger',
                footer: '<a href>Məlumatlar yenilənsinmi?</a>'
            }).then(function(result) {
                if (result.value) {
                    submit(form);
                }
            })
        } else {
            submit(form);
        }
    }

</script>




