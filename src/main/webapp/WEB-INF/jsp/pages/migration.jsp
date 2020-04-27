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
    <c:set var="upload" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'upload')}"/>
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">
                    <div class="form-group form-group-last">
                        <div class="alert alert-secondary" role="alert">
                            <div class="alert-icon"><i class="flaticon-warning kt-font-brand"></i></div>
                            <div class="alert-text">
                                Yüklənmə Excel faylından nəzərdə tutulmuşdur. [.xlsx] formatlı fayldan məlumatı yükləyə bilərsiniz.
                                Şablon excel faylı formasını <a href="<c:url value="/assets/template/non-working-day-example.xlsx" />" target="_blank">buradan endirə</a> bilərsiniz.
                            </div>
                        </div>
                    </div>
                    <form id="upload-form" action="/migration/db/upload" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>Strukturun</label>
                            <select name="organization" id="organization" class="custom-select form-control">
                                <option></option>
                                <c:forEach var="t" items="${organizations}">
                                    <option value="<c:out value="${t.id}"/>"><c:out value="${t.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Baza excel faylı</label>
                            <div></div>
                            <div class="custom-file">
                                <input type="file" name="file" class="custom-file-input" id="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <label class="custom-file-label" for="file">Baza faylını seçin</label>
                            </div>
                        </div>
                    </form>
                    <c:if test="${upload.status}">
                        <div class="row">
                            <div class="col-md-4 offset-md-4 text-right">
                                <button type="button" class="btn btn-primary" onclick="submit($('#upload-form'));">Yüklə</button>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('.custom-file-input').on('change', function() {
        var fileName = $(this).val();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    $( "#upload-form" ).validate({
        rules: {
            organization: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
            KTUtil.scrollTop();
            swal.close();
        },
    })
</script>