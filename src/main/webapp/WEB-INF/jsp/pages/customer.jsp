<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld" %>
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
                        <form:form modelAttribute="filter" id="filter" method="post" action="/crm/customer/filter">
                            <form:hidden path="organization.id" />
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
                                                <form:label path="person.firstName">Ad</form:label>
                                                <form:input path="person.firstName" cssClass="form-control"
                                                            placeholder="Adı daxil edin Məs: Səbuhi"/>
                                                <form:errors path="person.firstName" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.lastName">Soyad</form:label>
                                                <form:input path="person.lastName" cssClass="form-control"
                                                            placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                                <form:errors path="person.lastName" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                                <div class="input-group date">
                                                    <form:input path="person.birthday" autocomplete="off"
                                                                cssClass="form-control datepicker-element" date_="date_"
                                                                placeholder="dd.MM.yyyy"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.birthday" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                                <form:input path="person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                                <form:errors path="person.idCardSerialNumber" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                                <form:input path="person.idCardPinCode" cssClass="form-control" placeholder="Məs: 4HWL0AM"/>
                                                <form:errors path="person.idCardPinCode" cssClass="control-label alert-danger" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.email">Email</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.email" cssClass="form-control"
                                                                placeholder="example@example.com"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-at"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.email" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.mobilePhone" cssClass="form-control"
                                                                placeholder="505505550"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                                <div class="input-group">
                                                    <form:input path="person.contact.homePhone" cssClass="form-control"
                                                                placeholder="124555050"/>
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-phone"></i>
                                        </span>
                                                    </div>
                                                </div>
                                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.city">Şəhər</form:label>
                                                <form:select path="person.contact.city.id" cssClass="custom-select form-control">
                                                    <form:option value=""></form:option>
                                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                                </form:select>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="person.contact.address">Ünvan</form:label>
                                                <div class="input-group">
                                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-street-view"></i>
                                        </span>
                                                    </div>
                                                    <form:input path="person.contact.address" cssClass="form-control"
                                                                placeholder="Küçə adı, ev nömrəsi və s."/>
                                                </div>
                                                <form:errors path="person.contact.address" cssClass="control-label alert-danger"/>
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
                <span class="kt-portlet__body">
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                            <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                            <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                            <table class="table table-striped- table-bordered table-hover table-checkable"
                                   id="group_table">
                                <thead>
                                <tr>
                                    <th>KOD</th>
                                    <th>Ad Soyad Ata adı</th>
                                    <th>Struktur</th>
                                    <th>Şəhər</th>
                                    <th>Telefon</th>
                                    <th>Doğum tarixi</th>
                                    <th>Ş.v - nin seriya nömrəsi</th>
                                    <th>Ş.v - nin pin kodu</th>
                                    <th>Əməliyyat</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="t" items="${list.content}" varStatus="loop">
                                    <tr data="<c:out value="${t.id}" />">
                                        <td><a href="javascript:copyToClipboard('<c:out value="${t.id}" />')" class="kt-link kt-font-lg kt-font-bold kt-margin-t-5"><c:out value="${t.id}"/></a></td>
                                        <th>
                                            <c:out value="${t.person.fullName}"/>
                                        </th>
                                        <td><c:out value="${t.organization.name}"/></td>
                                        <th>
                                            <c:out value="${t.person.contact.city.name}"/>
                                        </th>
                                        <td><c:out value="${t.person.contact.mobilePhone}"/></td>
                                        <td><fmt:formatDate value="${t.person.birthday}" pattern="dd.MM.yyyy"/></td>
                                        <td><c:out value="${t.person.idCardSerialNumber}"/></td>
                                        <td><c:out value="${t.person.idCardPinCode}"/></td>
                                        <td nowrap class="text-center">
                                            <c:if test="${view.status}">
                                                 <a href="javascript:customer('view', $('#form'), '<c:out value="${t.id}"/>', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                                     <i class="<c:out value="${view.object.icon}"/>"></i>
                                                 </a>
                                            </c:if>
                                            <c:if test="${edit.status}">
                                                <a href="javascript:customer('edit', $('#form'), '<c:out value="${t.id}"/>', 'modal-operation', '<c:out value="${edit.object.name}" />');"
                                                   class="btn btn-sm btn-clean btn-icon btn-icon-md"
                                                   title="<c:out value="${edit.object.name}"/>">
                                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${delete.status}">
                                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.person.firstName}" /> <c:out value="${t.person.lastName}" /> <c:out value="${t.person.fatherName}" />');"
                                                   class="btn btn-sm btn-clean btn-icon btn-icon-md"
                                                   title="<c:out value="${delete.object.name}"/>">
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
                <form:form modelAttribute="form" id="form" method="post" action="/crm/customer" cssClass="form-group" enctype="multipart/form-data">
                    <form:hidden path="id"/>
                    <form:hidden path="active"/>
                    <form:hidden path="organization.id"/>
                    <form:hidden path="person.id"/>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.firstName">Ad</form:label>
                                <form:input path="person.firstName" cssClass="form-control"
                                            placeholder="Adı daxil edin Məs: Səbuhi"/>
                                <form:errors path="person.firstName" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.lastName">Soyad</form:label>
                                <form:input path="person.lastName" cssClass="form-control"
                                            placeholder="Soyadı daxil edin Məs: Vəliyev"/>
                                <form:errors path="person.lastName" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.fatherName">Ata adı</form:label>
                                <form:input path="person.fatherName" cssClass="form-control"
                                            placeholder="Ata adını daxil edin"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.birthday">Doğum tarixi</form:label>
                                <div class="input-group date">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-calendar"></i></span></div>
                                    <form:input path="person.birthday" autocomplete="off" cssClass="form-control datepicker-element" date_="date_" placeholder="dd.MM.yyyy"/>
                                </div>
                                <form:errors path="person.birthday" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row bg-light">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.idCardSerialNumber">Ş.v - nin seriya nömrəsi</form:label>
                                <form:input path="person.idCardSerialNumber" cssClass="form-control" placeholder="AA0822304"/>
                                <form:errors path="person.idCardSerialNumber" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.idCardPinCode">Ş.v - nin pin kodu</form:label>
                                <form:input path="person.idCardPinCode" cssClass="form-control" cssStyle="text-transform: uppercase;" maxlength="7" placeholder="Məs: 4HWL0AM"/>
                                <form:errors path="person.idCardPinCode" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <form:label path="description">Açıqlama</form:label>
                                <form:textarea path="description" cssClass="form-control"/>
                                <form:errors path="description" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <hr style="width: 100%"/>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.email">Email</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                    <form:input path="person.contact.email" cssClass="form-control" placeholder="example@example.com"/>
                                </div>
                                <form:errors path="person.contact.email" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.mobilePhone">Mobil nömrə</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.mobilePhone" cssClass="form-control" placeholder="505505550"/>
                                </div>
                                <form:errors path="person.contact.mobilePhone" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.homePhone">Şəhər nömrəsi</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.homePhone" cssClass="form-control" placeholder="124555050"/>
                                </div>
                                <form:errors path="person.contact.homePhone" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber1">Əlaqəli şəxs nömrəsi #1</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.relationalPhoneNumber1" cssClass="form-control" placeholder="505505550"/>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber1" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber2">Əlaqəli şəxs nömrəsi #2</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.relationalPhoneNumber2" cssClass="form-control" placeholder="505505550"/>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber2" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.contact.relationalPhoneNumber3">Əlaqəli şəxs nömrəsi #3</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                    <form:input path="person.contact.relationalPhoneNumber3" cssClass="form-control" placeholder="505505550"/>
                                </div>
                                <form:errors path="person.contact.relationalPhoneNumber3" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <form:label path="person.contact.geolocation">Geolocation</form:label>
                                <div class="input-group" >
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-map-marker"></i></span></div>
                                    <form:input path="person.contact.geolocation" cssClass="form-control" readonly="true"/>
                                </div>
                                <form:errors path="person.contact.geolocation" cssClass="control-label alert-danger" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.city.id">Şəhər</form:label>
                                <form:select path="person.contact.city.id" onchange="selectLivingCity($('#form'), $(this))" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.address">Ünvan</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-street-view"></i></span></div>
                                    <form:input path="person.contact.address" cssClass="form-control" placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.address" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <form:label path="person.contact.livingCity.id">Yaşadığı şəhər</form:label>
                                <form:select path="person.contact.livingCity.id" cssClass="custom-select form-control">
                                    <form:option value=""></form:option>
                                    <form:options items="${cities}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <form:label path="person.contact.livingAddress">Yaşadığı ünvan</form:label>
                                <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-street-view"></i></span></div>
                                    <form:input path="person.contact.livingAddress" cssClass="form-control"
                                                placeholder="Küçə adı, ev nömrəsi və s."/>
                                </div>
                                <form:errors path="person.contact.livingAddress" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Ş.v-nin ön hissəsi</label>
                                <div></div>
                                <div class="custom-file">
                                    <input type="file" name="file1" class="custom-file-input" id="file1" accept="image/*">
                                    <label class="custom-file-label" for="file1">Şəxsiyyət vəsiqəsi</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Ş.v-nin arxa hissəsi</label>
                                <div></div>
                                <div class="custom-file">
                                    <input type="file" name="file2" class="custom-file-input" id="file2" accept="image/*">
                                    <label class="custom-file-label" for="file2">Şəxsiyyət vəsiqəsi</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" id="image-content">

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

    $("#group_table").DataTable({
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
        pageLength: 100,
        order: [[2, 'asc']],
        drawCallback: function(settings) {
            var api = this.api();
            var rows = api.rows({page: 'current'}).nodes();
            var last = null;

            api.column(2, {page: 'current'}).data().each(function(group, i) {
                if (last !== group) {
                    $(rows).eq(i).before(
                        '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                    );
                    last = group;
                }
            });
        },
        columnDefs: [
            {
                targets: [2],
                visible: false
            }
        ]
    });

    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
            customer('view', $('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $( "#form" ).validate({
        rules: {
            "person.firstName": {
                required: true
            },
            "person.lastName": {
                required: true
            },
            'person.idCardPinCode': {
                required: true,
                maxlength: 7,
                minlength: 7
            },
            "person.contact.mobilePhone": {
                required: true
            },
            "person.contact.city.id": {
                required: true
            },
            "person.contact.address": {
                required: true
            },
            "person.contact.livingCity.id": {
                required: true
            },
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    });

    $("input[name='person.contact.email']").inputmask({
        mask: "*{1,20}[.*{1,20}][.*{1,20}][.*{1,20}]@*{1,20}[.*{2,6}][.*{1,2}]",
        greedy: false,
        onBeforePaste: function (pastedValue, opts) {
            pastedValue = pastedValue.toLowerCase();
            return pastedValue.replace("mailto:", "");
        },
        definitions: {
            '*': {
                validator: "[0-9A-Za-z!#$%&'*+/=?^_`{|}~\-]",
                cardinality: 1,
                casing: "lower"
            }
        }
    });

    $("input[name='person.contact.mobilePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='person.contact.homePhone']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='person.contact.relationalPhoneNumber1']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='person.contact.relationalPhoneNumber2']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    $("input[name='person.contact.relationalPhoneNumber3']").inputmask("mask", {
        "mask": "(999) 999-9999"
    });

    function showPosition(position) {
        var geolocation =  $("#form").find("input[name='person.contact.geolocation']");
        if($(geolocation).val().trim().length==0){
            $(geolocation).val(position.coords.latitude + ',' + position.coords.longitude);
        }
    }

    $(function(){
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        }
    });

    function selectLivingCity(form, element){
        $(form).find("select[name='person.contact.livingCity.id'] option[value="+$(element).val()+"]").attr("selected", "selected");
    }

    function getImages(form, personId){
        var content = '';
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/common/api/person/document/'+personId,
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {
                        $(form).find("#image-content").html('');
                    },
                    success: function(data) {
                        console.log(data);
                        $.each(data, function( index, value ) {
                            if(value.fileContent!==""){
                                content+='<div class="col-6 text-center">' +
                                    '<img style="max-width: 90%; max-height: 240px" src="data:image/jpeg;base64, '+value.fileContent+'" />' +
                                    '</div>';
                            }
                        });
                        $(form).find("#image-content").html(content);
                        swal.close();
                    },
                    error: function() {
                        swal.fire({
                            title: "Xəta baş verdi!",
                            html: "Şəkil tapılmadı!",
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

    function customer(oper, form, dataId, modal, modal_title){
        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/crm/api/customer/'+dataId,
                    type: 'GET',
                    dataType: 'text',
                    beforeSend: function() {

                    },
                    success: function(data) {
                        data = data.replace(/\&#034;/g, '"');
                        var obj = jQuery.parseJSON(data);
                        console.log(obj);
                        if(oper==="view"){
                            view(form, data, modal, modal_title)
                        } else if(oper==="edit"){
                            edit(form, data, modal, modal_title)
                        }
                        getImages(form, obj.person.id);
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




