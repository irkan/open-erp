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
<style>

    td { position: relative; }

    tr.strikeout td:before {
        content: " ";
        position: absolute;
        top: 46%;
        left: 0;
        border-bottom: 1px solid #e50f00;
        width: 100%;
    }

    tr.strikeout td:after {
        content: "\00B7";
        font-size: 1px;
    }

</style>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/payroll/advance/filter">
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
                                                <form:label path="advance">Avans</form:label>
                                                <form:select path="advance.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${advances}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="employee">Əməkdaş</form:label>
                                                <form:select  path="employee.id" cssClass="custom-select form-control select2-single" multiple="single">
                                                    <form:option value=""></form:option>
                                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                                        <optgroup label="${itemGroup.key}">
                                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                                        </optgroup>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="employee" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payedFrom">Qiymətdən</form:label>
                                                <form:input path="payedFrom" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="payedFrom" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="payed">Qiymətədək</form:label>
                                                <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="advanceDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="advanceDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="advanceDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="advanceDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="advanceDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="advanceDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="padding-top: 30px;">
                                            <div class="form-group">
                                                <label class="kt-checkbox kt-checkbox--brand">
                                                    <form:checkbox path="approve"/> Təsdiq edilənlər
                                                    <span></span>
                                                </label>
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
        <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
        <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
        <c:set var="credit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'credit')}"/>
        <c:set var="transfer" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'transfer')}"/>
        <c:set var="approve" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'approve')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <c:set var="canviewall" value="${utl:canViewAll(sessionScope.organization_selected)}"/>
        <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Avans</th>
        <th>Əməkdaş</th>
        <c:if test="${canviewall}">
            <th style="min-width: 70px;">Struktur</th>
        </c:if>
        <th>Məbləğ</th>
        <th>Tarix</th>
        <th>Açıqlama</th>
        <th>Formul</th>
        <th>Təsdiq edilib</th>
        <th>Tranzaksiya</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list.content}" varStatus="loop">
        <tr data="<c:out value="${t.id}" />" class="<c:out value="${(t.payed lt 0)?'strikeout':''}"/> ">
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.advance.name}" /></td>
            <td><span style="width: 160px;" class="kt-font-bolder"><c:out value="${t.employee.person.fullName}" /></span></td>
            <c:if test="${canviewall}">
                <td><c:out value="${t.organization.name}" /></td>
            </c:if>
            <td>
                <span style="width: 65px;" class="kt-font-bolder">
                    <span><c:out value="${t.payed}" /></span>
                    <span class="kt-font-bold font-italic font-size-10px">AZN</span>
                </span>
            </td>
            <td><span class="kt-font-bolder"><fmt:formatDate value = "${t.advanceDate}" pattern = "dd.MM.yyyy" /></span></td>
            <td><c:out value="${t.description}" /></td>
            <td><c:out value="${t.formula}" /></td>
            <td>
                <c:if test="${t.approve}">
                    <fmt:formatDate value = "${t.approveDate}" pattern = "dd.MM.yyyy HH:mm:ss" />
                </c:if>
            </td>
            <td>
                <c:if test="${t.transaction}">
                    <fmt:formatDate value = "${t.transactionDate}" pattern = "dd.MM.yyyy HH:mm:ss" />
                </c:if>
            </td>
            <td nowrap class="text-center">
                <c:if test="${approve.status and !t.approve}">
                    <a href="javascript:approve($('#advance-approve-form'), $('#advance-approve-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.description}" />', '<c:out value="${t.payed}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${approve.object.name}"/>">
                        <i class="<c:out value="${approve.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${view.status}">
                    <a href="javascript:advance('view', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                        <i class="<c:out value="${view.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${transfer.status and t.approve and !t.transaction}">
                    <a href="javascript:transfer($('#advance-transfer-form'), $('#advance-transfer-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.payed}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${transfer.object.name}"/>">
                        <i class="<c:out value="${transfer.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${credit.status and t.transaction and t.payed gt 0}">
                    <a href="javascript:credit($('#advance-credit-form'), $('#advance-credit-modal'), '<c:out value="${t.id}" />', '<c:out value="${t.payed}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${credit.object.name}"/>">
                        <i class="<c:out value="${credit.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${edit.status and !t.approve}">
                    <a href="javascript:advance('edit', $('#form'), '<c:out value="${t.id}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${delete.status and !t.approve}">
                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/payroll/advance" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="organization" />
                    <form:hidden path="active"/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="employee">Əməkdaş</form:label>
                                <form:select  path="employee" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <c:forEach var="itemGroup" items="${employees}" varStatus="itemGroupIndex">
                                        <optgroup label="${itemGroup.key}">
                                            <form:options items="${itemGroup.value}" itemLabel="person.fullName" itemValue="id"/>
                                        </optgroup>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="employee" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="advance">Avans</form:label>
                                <form:select  path="advance" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${advances}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <form:errors path="advance" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="payed">Məbləğ</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                                </div>
                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="advanceDate">Avans tarixi</form:label>
                                <div class="input-group date" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="advanceDate" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
                                </div>
                                <form:errors path="advanceDate" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
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

<div class="modal fade" id="advance-approve-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="advance-approve-form" method="post" action="/payroll/advance/approve" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="payed">Məbləğ</form:label>
                        <div class="input-group">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin"/>
                        </div>
                        <form:errors path="payed" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#advance-approve-form'));">Bəli, təsdiq edirəm!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="advance-transfer-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="advance-transfer-form" method="post" action="/payroll/advance/transfer" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="payed">Məbləğ</form:label>
                        <div class="input-group">
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                            <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin" readonly="true"/>
                        </div>
                        <form:errors path="payed" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" rows="4"/>
                        <form:errors path="description" cssClass="alert-danger control-label"/>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#advance-transfer-form'));">Tranzaksiya edilsin!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="advance-credit-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="advance-credit-form" method="post" action="/payroll/advance/credit" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <form:label path="payed">Məbləğ</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-usd"></i></span></div>
                                    <form:input path="payed" cssClass="form-control" placeholder="Məbləği daxil edin" readonly="true"/>
                                </div>
                                <form:errors path="payed" cssClass="alert-danger control-label"/>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#advance-credit-form'));">Təsdiq edilsin!</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    function approve(form, modal, id, description, payed){
        $(form).find("#id").val(id);
        $(form).find("#payed").val(payed);
        $(form).find("#description").val(description);

        $(modal).find(".modal-title").html('Təsdiq et!');
        $(modal).modal('toggle');
    }

    function transfer(form, modal, id, payed, description){
        $(form).find("#id").val(id);
        $(form).find("#payed").val(payed);
        $(form).find("textarea[name='description']").val(description);
        $(modal).find(".modal-title").html('Tranzaksiya et!');
        $(modal).modal('toggle');
    }

    function credit(form, modal, id, payed){
        $(form).find("#id").val(id);
        $(form).find("#payed").val(payed);
        $(form).find("#description").val(description);
        $(modal).find(".modal-title").html('Kredit əməliyyatı!');
        $(modal).modal('toggle');
    }

    $('#datatable tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
            advance('view', $('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

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
                width: '25px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    $( "#form" ).validate({
        rules: {
            employee: {
                required: true
            },
            advance: {
                required: true
            },
            payed: {
                required: true,
                number: true
            },
            advanceDate: {
                required: true
            },
            description: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#advance-approve-form" ).validate({
        rules: {
            payed: {
                required: true,
                number: true
            },
            description: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#advance-transfer-form" ).validate({
        rules: {
            id: {
                required: true,
                digits: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $( "#advance-credit-form" ).validate({
        rules: {
            id: {
                required: true,
                digits: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='payed']").inputmask('decimal', {
        rightAlignNumerics: false
    });

    function advance(oper, form, dataId, modal, modal_title){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/payroll/api/advance/'+dataId,
                    type: 'GET',
                    dataType: 'text',
                    beforeSend: function() {

                    },
                    success: function(data) {
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
</script>


