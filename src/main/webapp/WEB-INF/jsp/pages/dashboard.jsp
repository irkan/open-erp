<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 20.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-12">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/> text-center" style="letter-spacing: 3px;">
                    <h5 class="card-title font-weight-bolder"><span>Satış statistikaları</span></h5>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 gün / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1dayCount" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 həftə / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1weekCount" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 ay / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1monthCount" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 il / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1yearCount" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 gün / yığım</h6>
                    <hr/>
                    <div id="last1dayCollect" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 həftə / yığım</h6>
                    <hr/>
                    <div id="last1weekCollect" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 ay / yığım</h6>
                    <hr/>
                    <div id="last1monthCollect" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 il / yığım</h6>
                    <hr/>
                    <div id="last1yearCollect" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-12">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/> text-center" style="letter-spacing: 3px;">
                    <h5 class="card-title font-weight-bolder"><span>Servis statistikaları</span></h5>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 gün / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1dayCountService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 həftə / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1weekCountService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 ay / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1monthCountService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 il / müqavilə sayı</h6>
                    <hr/>
                    <div id="last1yearCountService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 gün / yığım</h6>
                    <hr/>
                    <div id="last1dayCollectService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 həftə / yığım</h6>
                    <hr/>
                    <div id="last1weekCollectService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 ay / yığım</h6>
                    <hr/>
                    <div id="last1monthCollectService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
            <div class="card border-0 mb-3">
                <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                    <h6 class="card-title font-weight-bolder text-center" style="letter-spacing: 2px">Son 1 il / yığım</h6>
                    <hr/>
                    <div id="last1yearCollectService" class="d-flex justify-content-center"></div>
                </div>
            </div>
        </div>
        <%--<c:forEach var="t" items="${organizations}" varStatus="loop">
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12 col-12 border-0">
                <div class="card border-0 mb-3">
                    <div class="card-body <c:out value="${random.nextInt(5) eq 1 ? 'kt-bg-light-danger' : random.nextInt(5) eq 2 ? 'kt-bg-light-warning' : random.nextInt(5) eq 3 ? 'kt-bg-light-info' : random.nextInt(5) eq 4 ? 'kt-bg-light-success' : random.nextInt(5) eq 5 ? 'kt-bg-light-primary' : 'kt-bg-light-brand'}"/>">
                        <h5 class="card-title font-weight-bolder"><span id="baku-branch-title"><c:out value="${t.name}"/></span></h5>
                        <div id="chart" class="d-flex justify-content-center"></div>
                    </div>
                </div>
            </div>
        </c:forEach>--%>
    </div>
</div>
<%--<script src="<c:url value="/assets/js/apexchart/apexcharts.min.js" />" type="text/javascript"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

<script>
    $(function(){
        var last1dayCountOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1weekCountOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1monthCountOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1yearCountOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};

        var last1dayCollectOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1weekCollectOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1monthCollectOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1yearCollectOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};

        var last1dayCountServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1weekCountServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1monthCountServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1yearCountServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};

        var last1dayCollectServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1weekCollectServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1monthCollectServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};
        var last1yearCollectServiceOptions = {series: [], chart: {type: 'pie'}, labels: [], responsive: [{breakpoint: 480, options: {chart: {width: 200}, legend: {position: 'bottom'}}}]};

        var last1dayCount = new ApexCharts(document.querySelector("#last1dayCount"), last1dayCountOptions);
        var last1weekCount = new ApexCharts(document.querySelector("#last1weekCount"), last1weekCountOptions);
        var last1monthCount = new ApexCharts(document.querySelector("#last1monthCount"), last1monthCountOptions);
        var last1yearCount = new ApexCharts(document.querySelector("#last1yearCount"), last1yearCountOptions);

        var last1dayCollect = new ApexCharts(document.querySelector("#last1dayCollect"), last1dayCollectOptions);
        var last1weekCollect = new ApexCharts(document.querySelector("#last1weekCollect"), last1weekCollectOptions);
        var last1monthCollect = new ApexCharts(document.querySelector("#last1monthCollect"), last1monthCollectOptions);
        var last1yearCollect = new ApexCharts(document.querySelector("#last1yearCollect"), last1yearCollectOptions);

        var last1dayCountService = new ApexCharts(document.querySelector("#last1dayCountService"), last1dayCountServiceOptions);
        var last1weekCountService = new ApexCharts(document.querySelector("#last1weekCountService"), last1weekCountServiceOptions);
        var last1monthCountService = new ApexCharts(document.querySelector("#last1monthCountService"), last1monthCountServiceOptions);
        var last1yearCountService = new ApexCharts(document.querySelector("#last1yearCountService"), last1yearCountServiceOptions);

        var last1dayCollectService = new ApexCharts(document.querySelector("#last1dayCollectService"), last1dayCollectServiceOptions);
        var last1weekCollectService = new ApexCharts(document.querySelector("#last1weekCollectService"), last1weekCollectServiceOptions);
        var last1monthCollectService = new ApexCharts(document.querySelector("#last1monthCollectService"), last1monthCollectServiceOptions);
        var last1yearCollectService = new ApexCharts(document.querySelector("#last1yearCollectService"), last1yearCollectServiceOptions);


        swal.fire({
            text: 'Proses davam edir...',
            allowOutsideClick: false,
            onOpen: function() {
                swal.showLoading();
                $.ajax({
                    url: '/report/api/sales-detail',
                    type: 'GET',
                    dataType: 'json',
                    beforeSend: function() {

                    },
                    success: function(data) {
                        $.each(data, function(key, val){

                            last1dayCountOptions.labels.push(val.NAME);
                            last1dayCountOptions.series.push(round(val.SON_1_GUNLUK_AKTIV_SATIS_MUQAVILELERIN_SAYI, 0));
                            last1weekCountOptions.labels.push(val.NAME);
                            last1weekCountOptions.series.push(round(val.SON_1_HEFTELIK_AKTIV_SATIS_MUQAVILELERIN_SAYI, 0));
                            last1monthCountOptions.labels.push(val.NAME);
                            last1monthCountOptions.series.push(round(val.SON_1_AYLIQ_AKTIV_SATIS_MUQAVILELERIN_SAYI, 0));
                            last1yearCountOptions.labels.push(val.NAME);
                            last1yearCountOptions.series.push(round(val.SON_1_ILLIK_AKTIV_SATIS_MUQAVILELERIN_SAYI, 0));

                            last1dayCollectOptions.labels.push(val.NAME);
                            last1dayCollectOptions.series.push(round(val.SON_1_GUNLUK_SATIS_YIGIMLARIN_CEMI, 0));
                            last1weekCollectOptions.labels.push(val.NAME);
                            last1weekCollectOptions.series.push(round(val.SON_1_HEFTELIK_SATIS_YIGIMLARIN_CEMI, 0));
                            last1monthCollectOptions.labels.push(val.NAME);
                            last1monthCollectOptions.series.push(round(val.SON_1_AYLIQ_SATIS_YIGIMLARIN_CEMI, 0));
                            last1yearCollectOptions.labels.push(val.NAME);
                            last1yearCollectOptions.series.push(round(val.SON_1_ILLIK_SATIS_YIGIMLARIN_CEMI, 0));


                            last1dayCountServiceOptions.labels.push(val.NAME);
                            last1dayCountServiceOptions.series.push(round(val.SON_1_GUNLUK_AKTIV_SERVIS_MUQAVILELERIN_SAYI, 0));
                            last1weekCountServiceOptions.labels.push(val.NAME);
                            last1weekCountServiceOptions.series.push(round(val.SON_1_HEFTELIK_AKTIV_SERVIS_MUQAVILELERIN_SAYI, 0));
                            last1monthCountServiceOptions.labels.push(val.NAME);
                            last1monthCountServiceOptions.series.push(round(val.SON_1_AYLIQ_AKTIV_SERVIS_MUQAVILELERIN_SAYI, 0));
                            last1yearCountServiceOptions.labels.push(val.NAME);
                            last1yearCountServiceOptions.series.push(round(val.SON_1_ILLIK_AKTIV_SERVIS_MUQAVILELERIN_SAYI, 0));

                            last1dayCollectServiceOptions.labels.push(val.NAME);
                            last1dayCollectServiceOptions.series.push(round(val.SON_1_GUNLUK_SERVIS_YIGIMLARIN_CEMI, 0));
                            last1weekCollectServiceOptions.labels.push(val.NAME);
                            last1weekCollectServiceOptions.series.push(round(val.SON_1_HEFTELIK_SERVIS_YIGIMLARIN_CEMI, 0));
                            last1monthCollectServiceOptions.labels.push(val.NAME);
                            last1monthCollectServiceOptions.series.push(round(val.SON_1_AYLIQ_SERVIS_YIGIMLARIN_CEMI, 0));
                            last1yearCollectServiceOptions.labels.push(val.NAME);
                            last1yearCollectServiceOptions.series.push(round(val.SON_1_ILLIK_SERVIS_YIGIMLARIN_CEMI, 0));
                        });

                        last1dayCount.render();
                        last1weekCount.render();
                        last1monthCount.render();
                        last1yearCount.render();

                        last1dayCollect.render();
                        last1weekCollect.render();
                        last1monthCollect.render();
                        last1yearCollect.render();

                        last1dayCountService.render();
                        last1weekCountService.render();
                        last1monthCountService.render();
                        last1yearCountService.render();

                        last1dayCollectService.render();
                        last1weekCollectService.render();
                        last1monthCollectService.render();
                        last1yearCollectService.render();


                        swal.close();
                    },
                    complete: function(){
                        swal.close();
                    },
                    error: function() {
                        swal.close();
                    }
                })
            }
        })
    });
</script>