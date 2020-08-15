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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/admin/notification/filter">
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
                                                <form:label path="type">Tip</form:label>
                                                <form:select path="type.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${notifications}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="to">Kimə?</form:label>
                                                <form:input path="to" cssClass="form-control" placeholder="Email və ya telefon nömrəsi" />
                                                <form:errors path="to" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="subject">Başlıq</form:label>
                                                <form:input path="subject" cssClass="form-control" placeholder="Başlığı daxil edin" />
                                                <form:errors path="subject" cssClass="alert alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="message">Mesaj mətni</form:label>
                                                <form:input path="message" cssClass="form-control" placeholder="Mesajı daxil edin"/>
                                                <form:errors path="message" cssClass="alert alert-danger"/>
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
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sendingDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="sendingDateFrom" autocomplete="off"
                                                                cssClass="form-control datetimepicker-element" date_="datetime_"
                                                                placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="sendingDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="sendingDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="sendingDate" autocomplete="off"
                                                                cssClass="form-control datetimepicker-element" date_="datetime_"
                                                                placeholder="dd.MM.yyyy HH:mm"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="sendingDate" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="send">Tip</form:label>
                                                <form:select path="send" cssClass="custom-select form-control">
                                                    <form:option value="0">0</form:option>
                                                    <form:option value="1">1</form:option>
                                                    <form:option value="2">2</form:option>
                                                </form:select>
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
<table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tip</th>
        <th>Struktur</th>
        <th>Kimdən</th>
        <th>Kimə</th>
        <th>Başlıq</th>
        <th>Mesaj</th>
        <th>Açıqlama</th>
        <th>Göndərildi</th>
        <th>Status</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list.content}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <th><c:out value="${t.id}" /></th>
            <td><c:out value="${t.type.name}" /></td>
            <td><c:out value="${t.organization.name}" /></td>
            <td><c:out value="${t.from}" /></td>
            <td><c:out value="${t.to}" /></td>
            <td><c:out value="${t.subject}" escapeXml="false" /></td>
            <th><c:out value="${t.message}" escapeXml="false" /></th>
            <td><c:out value="${t.description}" /></td>
            <td><fmt:formatDate value = "${t.sendingDate}" pattern = "dd.MM.yyyy hh:mm:ss" /></td>
            <td class="text-center">
                <c:choose>
                    <c:when test="${t.send==1}">
                        <span class="kt-font-bold kt-font-success"><i class="la la-check"></i></span>
                    </c:when>
                    <c:when test="${t.send==2}">
                        <span class="kt-font-bold kt-font-danger"><i class="la la-remove"></i></span>
                    </c:when>
                    <c:otherwise>
                        <span class="kt-spinner kt-spinner--md kt-spinner--danger" style="margin-left: -20px;"></span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td nowrap class="text-center">
                <c:if test="${view.status}">
                    <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                        <i class="<c:out value="${view.object.icon}"/>"></i>
                    </a>
                </c:if>
                <c:if test="${delete.status}">
                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.to}" /><br/><c:out value="${t.subject}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/admin/notification" cssClass="form-group">
                    <form:hidden path="id"/>
                    <form:hidden path="active" value="1"/>
                    <form:hidden path="organization" value="${sessionScope.organization.id}"/>
                    <div class="form-group">
                        <c:forEach var="t" items="${notifications}" varStatus="loop">
                            <label class="kt-radio kt-radio--brand">
                                <form:radiobutton path="type" value="${t.id}"/> <c:out value="${t.name}"/>
                                <span></span>
                            </label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:forEach>
                        <form:errors path="type" cssClass="control-label alert alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="to">Kimə?</form:label>
                        <form:input path="to" cssClass="form-control" placeholder="Email və ya telefon nömrəsi" />
                        <form:errors path="to" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="subject">Başlıq</form:label>
                        <form:input path="subject" cssClass="form-control" placeholder="Başlığı daxil edin" />
                        <form:errors path="subject" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="message">Mesaj mətni</form:label>
                        <form:textarea path="message" cssClass="form-control" placeholder="Mesajı daxil edin" />
                        <form:errors path="message" cssClass="alert alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
                        <form:errors path="description" cssClass="alert alert-danger"/>
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

<script>
    $('#datatable tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });
    $( "#form" ).validate({
        rules: {
            type: {
                required: true
            },
            to: {
                required: true
            },
            subject: {
                required: true
            },
            message: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })

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
</script>


