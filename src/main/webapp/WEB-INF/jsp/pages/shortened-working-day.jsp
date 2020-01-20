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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/hr/shortened-working-day/filter">
                            <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <form:label path="id">KOD</form:label>
                                                <form:input path="id" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="id" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <form:label path="shortenedTime">Qısalma saatı</form:label>
                                                <form:input path="shortenedTime" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="shortenedTime" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="identifier">İdentifikator</form:label>
                                                <form:select  path="identifier" cssClass="custom-select form-control">
                                                    <form:options items="${identifiers}" itemLabel="name" itemValue="attr1" />
                                                </form:select>
                                                <form:errors path="identifier" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="description">Açıqlama</form:label>
                                                <form:input path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
                                                <form:errors path="description" cssClass="alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="workingDateFrom">Tarixdən</form:label>
                                                <div class="input-group date">
                                                    <form:input path="workingDateFrom" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="workingDateFrom" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="workingDate">Tarixədək</form:label>
                                                <div class="input-group date">
                                                    <form:input path="workingDate" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date="date"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="workingDate" cssClass="control-label alert-danger"/>
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
                            <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
                                <thead>
                                <tr>
                                    <th>№</th>
                                    <th>ID</th>
                                    <th>İş günü</th>
                                    <th>İdentifikator</th>
                                    <th>Qısaldılmış iş saatı</th>
                                    <th>Açıqlama</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${utl:toJson(t)}" />">
                                        <td>${loop.index + 1}</td>
                                        <td><c:out value="${t.id}" /></td>
                                        <td><fmt:formatDate value = "${t.workingDate}" pattern = "dd.MM.yyyy" /></td>
                                        <td><c:out value="${t.identifier}" /></td>
                                        <td><c:out value="${t.shortenedTime}" /> saat</td>
                                        <td><c:out value="${t.description}" /></td>
                                        <td nowrap class="text-center">
                                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                                            <c:choose>
                                                <c:when test="${view.status}">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                        <i class="la <c:out value="${view.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                                            <c:choose>
                                                <c:when test="${edit.status}">
                                                    <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                                        <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                                            <c:choose>
                                                <c:when test="${delete.status}">
                                                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<fmt:formatDate value = "${t.workingDate}" pattern = "dd.MM.yyyy" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                                        <i class="<c:out value="${delete.object.icon}"/>"></i>
                                                    </a>
                                                </c:when>
                                            </c:choose>
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
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/hr/shortened-working-day" cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <div class="form-group">
                        <form:label path="workingDate">Qeyri iş günü</form:label>
                        <div class="input-group date" >
                            <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                            <form:input path="workingDate" autocomplete="off" cssClass="form-control datepicker-element" date="date" placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="workingDate" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="identifier">İdentifikator</form:label>
                        <form:select  path="identifier" cssClass="custom-select form-control">
                            <form:options items="${identifiers}" itemLabel="name" itemValue="attr1" />
                        </form:select>
                        <form:errors path="identifier" cssClass="control-label alert-danger" />
                    </div>
                    <div class="form-group">
                        <form:label path="shortenedTime">Qısaldılmış iş saatı</form:label>
                        <form:select  path="shortenedTime" cssClass="custom-select form-control">
                            <form:option value=""></form:option>
                            <form:options items="${shortened_times}" itemLabel="name" itemValue="attr1" />
                        </form:select>
                        <form:errors path="shortenedTime" cssClass="alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
                        <form:errors path="description" cssClass="alert-danger"/>
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

<div class="modal fade" id="upload-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group form-group-last">
                    <div class="alert alert-secondary" role="alert">
                        <div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
                        <div class="alert-text">
                            Yüklənmə Excel faylından nəzərdə tutulmuşdur. [.xlsx] formatlı fayldan məlumatı yükləyə bilərsiniz.
                            Şablon excel faylı formasını <a href="<c:url value="/assets/template/shortened-working-day-example.xlsx" />" target="_blank">buradan endirə</a> bilərsiniz.
                        </div>
                    </div>
                </div>
                <form id="upload-form" action="/hr/shortened-working-day/upload" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label>Qısaldılmış iş günləri excel faylı</label>
                        <div></div>
                        <div class="custom-file">
                            <input type="file" name="file" class="custom-file-input" id="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                            <label class="custom-file-label" for="file">Qısaldılmış iş günləri faylını seçin</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#upload-form'));">Yüklə</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });
</script>

<script>
    <c:if test="${edit.status}">
    $('#datatable tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>

    $("#datatable").DataTable({
        responsive: true,
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
            workingDate: {
                required: true
            },
            identifier: {
                required: true
            },
            shortenedTime: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>