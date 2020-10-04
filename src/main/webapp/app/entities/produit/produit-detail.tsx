import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';

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
    <div>
      <div>
        <label className="form__label">Fiche produit</label>
      </div>
      <br />
      <Row>
        <Col md="4">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Paramètres généraux</i>
              <dt>
                <span id="reference">Reference</span>
              </dt>
              <dd>{produitEntity.reference}</dd>
              <dt>
                <span id="nom">Nom</span>
              </dt>
              <dd>{produitEntity.nom}</dd>
              <dt>
                <span id="description">Description</span>
              </dt>
              <dd>{produitEntity.description}</dd>
              <dt>
                <span id="etat">Etat</span>
              </dt>
              <dd>{produitEntity.etat ? 'Actif' : 'Inactif'}</dd>
              <dt>
                <span id="marque">Marque</span>
              </dt>
              <dd>{produitEntity.marque}</dd>

              <dt>
                <span id="nature">Nature</span>
              </dt>
              <dd>{produitEntity.nature}</dd>
              <dt>
                <span id="sourceProduit">Source Produit</span>
              </dt>
              <dd>{produitEntity.sourceProduit}</dd>
              <dt>
                <span id="image">Image</span>
              </dt>
              <dd>
                {produitEntity.imageUrl ? (
                   <img src={`media/${produitEntity.imageUrl}`} style={{ maxHeight: '30px' }} />
                ) : null}
              </dd>
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
            </AvGroup>
          </AvForm>
        </Col>
        <Col md="4">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Prix</i>
              <dt>
                <span id="prixHT">Prix HT</span>
              </dt>
              <dd>{produitEntity.prixHT}</dd>
              <dt>
                <span id="tauxTVA">Taux TVA</span>
              </dt>
              <dd>{produitEntity.tauxTVA}</dd>
              <dt>
                <span id="prixTTC">Prix TTC</span>
              </dt>
              <dd>{produitEntity.prixTTC}</dd>
              <dt>
                <span id="devise">Devise</span>
              </dt>
              <dd>{produitEntity.devise}</dd>
              <dt>
                <span id="rating">Rating</span>
              </dt>
              <dd>{produitEntity.rating}</dd>
              <dt>
                <span id="eligibleRemise">Eligible Remise</span>
              </dt>
              <dd>{produitEntity.eligibleRemise ? 'Actif' : 'Inactif'}</dd>
              <dt>
                <span id="remise">Remise</span>
              </dt>
              <dd>{produitEntity.remise}</dd>
              <dt>
                <span id="debutPromo">Debut Promo</span>
              </dt>
              <dd>
                {produitEntity.debutPromo ? (
                  <TextFormat value={produitEntity.debutPromo} type="date" format={APP_LOCAL_DATE_FORMAT} />
                ) : null}
              </dd>
              <dt>
                <span id="finPromo">Fin Promo</span>
              </dt>
              <dd>
                {produitEntity.finPromo ? <TextFormat value={produitEntity.finPromo} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
              </dd>
            </AvGroup>
          </AvForm>
        </Col>
        <Col md="4">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Stock</i>
              <dt>
                <span id="stockMinimum">Stock Minimum</span>
              </dt>
              <dd>{produitEntity.stockMinimum}</dd>
              <dt>
                <span id="quantiteVenteMax">Quantite Vente Max</span>
              </dt>
              <dd>{produitEntity.quantiteVenteMax}</dd>
              <dt>
                <span id="horsStock">Hors Stock</span>
              </dt>
              <dd>{produitEntity.horsStock ? 'Actif' : 'Inactif'}</dd>
              <dt>
                <span id="typeService">Type Service</span>
              </dt>
              <dd>{produitEntity.typeService ? 'Actif' : 'Inactif'}</dd>
              <dt>
                <span id="datePremption">Date Premption</span>
              </dt>
              <dd>
                {produitEntity.datePremption ? (
                  <TextFormat value={produitEntity.datePremption} type="date" format={APP_LOCAL_DATE_FORMAT} />
                ) : null}
              </dd>
            </AvGroup>
          </AvForm>
        </Col>
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
      </Row>
    </div>
  );
};

const mapStateToProps = ({ produit }: IRootState) => ({
  produitEntity: produit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitDetail);
