select o.name,
       (select count(*) from sales s1 where s1.organization_id=o.id) melumat_sayi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and s1.organization_id=o.id) son_1_gunluk_aktiv_muqavilelerin_sayi,
       (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and i1.organization_id=o.id) son_1_gunluk_yigimlarin_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and s1.organization_id=o.id) son_1_heftelik_aktiv_muqavilelerin_sayi,
       (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and i1.organization_id=o.id) son_1_heftelik_yigimlarin_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and s1.organization_id=o.id) son_1_ayliq_aktiv_muqavilelerin_sayi,
       (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and i1.organization_id=o.id) son_1_ayliq_yigimlarin_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and s1.organization_id=o.id) son_1_illik_aktiv_muqavilelerin_sayi,
       (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and i1.organization_id=o.id) son_1_illik_yigimlarin_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.organization_id=o.id) aktiv_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.organization_id=o.id) aktiv_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.organization_id=o.id) qaytarilmis_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.organization_id=o.id) qaytarilmis_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1 where s1.is_active=0 and s1.organization_id=o.id) silinmis_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=0 and s1.organization_id=o.id) silinmis_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.organization_id=o.id) tesdiq_gozleyen_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.organization_id=o.id) tesdiq_gozleyen_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.organization_id=o.id) nagd_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.organization_id=o.id) nagd_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.organization_id=o.id) kredit_muqavilelerinin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.organization_id=o.id) kredit_muqavilelerinin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) endirimli_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) endirimli_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) endirimli_olmayan_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) endirimli_olmayan_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.organization_id=o.id) bitmemiw_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.organization_id=o.id) bitmemis_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.organization_id=o.id)  bitmiw_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.organization_id=o.id)  bitmis_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) bitmemis_endirimli_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) bitmemis_endirimli_muqavilelerin_meblegleri_cemi,
       (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) bitmemis_endirimli_olmayan_muqavilelerin_sayi,
       (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) bitmemis_endirimli_olmayan_muqavilelerin_meblegleri_cemi,
       ((select IFNULL(sum(IFNULL(p1.last_price,0)-(select IFNULL(sum(i1.price), 0) from invoice i1 where i1.sales_id=s1.id and i1.is_active=1 and i1.is_approve=1)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.is_cash=1 and s1.organization_id=o.id) +
        (select IFNULL(sum(p1.schedule_price), 0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.is_cash=0 and s1.organization_id=o.id)) planlanlanmis_yigim_meblegi,
       5+5
from organization o where o.is_active=1;




update invoice i2, sales s2 set invoice.invoice_date=s2.approve_date, i2.approve_date=s2.approve_date where i2.sales_id=s2.id and i2.id in (select i1.id from sales s1, invoice i1 where s1.id=i1.sales_id and i1.approve_date is not null and s1.approve_date is not null and i1.sales_id in (select id from sales s1 where (s1.id>=105786 and s1.id<=106447) or (s1.id>=100001 and s1.id<=105652)) and ((i1.approve_date between '2020-07-09 19:00:30' and '2020-07-09 23:00:25') or (i1.approve_date between '2020-06-29 20:04:22' and '2020-06-30 16:10:41')));
commit;
