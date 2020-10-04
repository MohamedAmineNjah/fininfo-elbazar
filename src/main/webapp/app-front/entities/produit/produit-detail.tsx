import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './produit.reducer';
import { IProduit } from 'app/shared/model/produit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProduitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProduitDetail = (props: IProduitDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { produitEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.produit.detail.title">Produit</Translate> [<b>{produitEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="reference">
              <Translate contentKey="elbazarApp.produit.reference">Reference</Translate>
            </span>
          </dt>
          <dd>{produitEntity.reference}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.produit.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{produitEntity.nom}</dd>
          <dt>
            <span id="codeBarre">
              <Translate contentKey="elbazarApp.produit.codeBarre">Code Barre</Translate>
            </span>
          </dt>
          <dd>{produitEntity.codeBarre}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="elbazarApp.produit.description">Description</Translate>
            </span>
          </dt>
          <dd>{produitEntity.description}</dd>
          <dt>
            <span id="etat">
              <Translate contentKey="elbazarApp.produit.etat">Etat</Translate>
            </span>
          </dt>
          <dd>{produitEntity.etat ? 'true' : 'false'}</dd>
          <dt>
            <span id="marque">
              <Translate contentKey="elbazarApp.produit.marque">Marque</Translate>
            </span>
          </dt>
          <dd>{produitEntity.marque}</dd>
          <dt>
            <span id="nature">
              <Translate contentKey="elbazarApp.produit.nature">Nature</Translate>
            </span>
          </dt>
          <dd>{produitEntity.nature}</dd>
          <dt>
            <span id="stockMinimum">
              <Translate contentKey="elbazarApp.produit.stockMinimum">Stock Minimum</Translate>
            </span>
          </dt>
          <dd>{produitEntity.stockMinimum}</dd>
          <dt>
            <span id="quantiteVenteMax">
              <Translate contentKey="elbazarApp.produit.quantiteVenteMax">Quantite Vente Max</Translate>
            </span>
          </dt>
          <dd>{produitEntity.quantiteVenteMax}</dd>
          <dt>
            <span id="horsStock">
              <Translate contentKey="elbazarApp.produit.horsStock">Hors Stock</Translate>
            </span>
          </dt>
          <dd>{produitEntity.horsStock ? 'true' : 'false'}</dd>
          <dt>
            <span id="typeService">
              <Translate contentKey="elbazarApp.produit.typeService">Type Service</Translate>
            </span>
          </dt>
          <dd>{produitEntity.typeService ? 'true' : 'false'}</dd>
          <dt>
            <span id="datePremption">
              <Translate contentKey="elbazarApp.produit.datePremption">Date Premption</Translate>
            </span>
          </dt>
          <dd>
            {produitEntity.datePremption ? (
              <TextFormat value={produitEntity.datePremption} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="prixHT">
              <Translate contentKey="elbazarApp.produit.prixHT">Prix HT</Translate>
            </span>
          </dt>
          <dd>{produitEntity.prixHT}</dd>
          <dt>
            <span id="tauxTVA">
              <Translate contentKey="elbazarApp.produit.tauxTVA">Taux TVA</Translate>
            </span>
          </dt>
          <dd>{produitEntity.tauxTVA}</dd>
          <dt>
            <span id="prixTTC">
              <Translate contentKey="elbazarApp.produit.prixTTC">Prix TTC</Translate>
            </span>
          </dt>
          <dd>{produitEntity.prixTTC}</dd>
          <dt>
            <span id="devise">
              <Translate contentKey="elbazarApp.produit.devise">Devise</Translate>
            </span>
          </dt>
          <dd>{produitEntity.devise}</dd>
          <dt>
            <span id="sourceProduit">
              <Translate contentKey="elbazarApp.produit.sourceProduit">Source Produit</Translate>
            </span>
          </dt>
          <dd>{produitEntity.sourceProduit}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="elbazarApp.produit.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{produitEntity.rating}</dd>
          <dt>
            <span id="eligibleRemise">
              <Translate contentKey="elbazarApp.produit.eligibleRemise">Eligible Remise</Translate>
            </span>
          </dt>
          <dd>{produitEntity.eligibleRemise ? 'true' : 'false'}</dd>
          <dt>
            <span id="remise">
              <Translate contentKey="elbazarApp.produit.remise">Remise</Translate>
            </span>
          </dt>
          <dd>{produitEntity.remise}</dd>
          <dt>
            <span id="debutPromo">
              <Translate contentKey="elbazarApp.produit.debutPromo">Debut Promo</Translate>
            </span>
          </dt>
          <dd>
            {produitEntity.debutPromo ? <TextFormat value={produitEntity.debutPromo} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="finPromo">
              <Translate contentKey="elbazarApp.produit.finPromo">Fin Promo</Translate>
            </span>
          </dt>
          <dd>
            {produitEntity.finPromo ? <TextFormat value={produitEntity.finPromo} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="image">
              <Translate contentKey="elbazarApp.produit.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {produitEntity.image ? (
              <div>
                {produitEntity.imageContentType ? (
                  <a onClick={openFile(produitEntity.imageContentType, produitEntity.image)}>
                    <img src={`data:${produitEntity.imageContentType};base64,${produitEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {produitEntity.imageContentType}, {byteSize(produitEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="creeLe">
              <Translate contentKey="elbazarApp.produit.creeLe">Cree Le</Translate>
            </span>
          </dt>
          <dd>{produitEntity.creeLe ? <TextFormat value={produitEntity.creeLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="creePar">
              <Translate contentKey="elbazarApp.produit.creePar">Cree Par</Translate>
            </span>
          </dt>
          <dd>{produitEntity.creePar}</dd>
          <dt>
            <span id="modifieLe">
              <Translate contentKey="elbazarApp.produit.modifieLe">Modifie Le</Translate>
            </span>
          </dt>
          <dd>
            {produitEntity.modifieLe ? <TextFormat value={produitEntity.modifieLe} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiePar">
              <Translate contentKey="elbazarApp.produit.modifiePar">Modifie Par</Translate>
            </span>
          </dt>
          <dd>{produitEntity.modifiePar}</dd>
          <dt>
            <Translate contentKey="elbazarApp.produit.categorie">Categorie</Translate>
          </dt>
          <dd>{produitEntity.categorieNom ? produitEntity.categorieNom : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.produit.sousCategorie">Sous Categorie</Translate>
          </dt>
          <dd>{produitEntity.sousCategorieNom ? produitEntity.sousCategorieNom : ''}</dd>
          <dt>
            <Translate contentKey="elbazarApp.produit.unite">Unite</Translate>
          </dt>
          <dd>{produitEntity.uniteCode ? produitEntity.uniteCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/produit" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/produit/${produitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ produit }: IRootState) => ({
  produitEntity: produit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitDetail);
