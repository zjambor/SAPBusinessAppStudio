using { sap.capire.bookshop as my } from '../db/schema';
using { API_BUSINESS_PARTNER as external } from '../../../../cloud-cap-samples-openSAP-week4-unit1-final/packages/bookshop/srv/external/API_BUSINESS_PARTNER.csn';

@path:'/browse'
service CatalogService {

  @readonly entity Books as SELECT from my.Books {*,
    author.name as author
  } excluding { createdBy, modifiedBy };

  @readonly entity BusinessPartners as projection on external.A_BusinessPartner {
      key BusinessPartner as ID,
      FirstName,
      MiddleName,
      LastName,
      BusinessPartnerIsBlocked
  };

  @requires_: 'authenticated-user'
  @insertonly entity Orders as projection on my.Orders;

}
