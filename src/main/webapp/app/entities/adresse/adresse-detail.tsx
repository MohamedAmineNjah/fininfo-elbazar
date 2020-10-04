import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './adresse.reducer';
import { IAdresse } from 'app/shared/model/adresse.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdresseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AdresseDetail = (props: IAdresseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { adresseEntity } = props;
  return (
    <div>
      <div>
        <label className="form__label">Fiche adresse</label>
      </div>
      <br />
      <Row className="justify-content-center">
        <Col md="4">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Paramètres généraux</i>

              <dt>
                <span id="principale">
                  <Translate contentKey="elbazarApp.adresse.principale">Principale</Translate>
                </span>
              </dt>
              <dd>{adresseEntity.principale ? 'Oui' : 'Non'}</dd>
              <dt>
                <span id="prenom">
                  <Translate contentKey="elbazarApp.adresse.prenom">Prenom</Translate>
                </span>
              </dt>
              <dd>{adresseEntity.prenom}</dd>
              <dt>
                <span id="nom">
                  <Translate contentKey="elbazarApp.adresse.nom">Nom</Translate>
                </span>
              </dt>
              <dd>{adresseEntity.nom}</dd>
              <dt>
                <span id="telephone">
                  <Translate contentKey="elbazarApp.adresse.telephone">Telephone</Translate>
                </span>
              </dt>
              <dd>{adresseEntity.telephone}</dd>
              <dt>
                <span id="mobile">
                  <Translate contentKey="elbazarApp.adresse.mobile">Mobile</Translate>
                </span>
              </dt>
              <dd>{adresseEntity.mobile}</dd>

              <dt>
                <Translate contentKey="elbazarApp.adresse.client">Client</Translate>
              </dt>
              <dd>{adresseEntity.clientId ? adresseEntity.clientId : ''}</dd>
            </AvGroup>
          </AvForm>
        </Col>
        <Col md="6">
          <AvForm>
            <AvGroup className="productborder">
              <i className="bordertitle">Paramètres généraux</i>
              <Row>
                <Col md="6">
                  <dt>
                    <span id="adresse">
                      <Translate contentKey="elbazarApp.adresse.adresse">Adresse</Translate>
                    </span>
                  </dt>
                  <dd>{adresseEntity.adresse}</dd>
                  <dt>
                    <span id="gouvernorat">
                      <Translate contentKey="elbazarApp.adresse.gouvernorat">Gouvernorat</Translate>
                    </span>
                  </dt>
                  <dd>{adresseEntity.gouvernorat}</dd>
                  <dt>
                    <span id="ville">
                      <Translate contentKey="elbazarApp.adresse.ville">Ville</Translate>
                    </span>
                  </dt>
                  <dd>{adresseEntity.ville}</dd>
                  <dt>
                    <span id="localite">
                      <Translate contentKey="elbazarApp.adresse.localite">Localite</Translate>
                    </span>
                  </dt>
                  <dd>{adresseEntity.localite}</dd>
                </Col>
                <Col md="6">
                  <dt>
                    <span id="indication">
                      <Translate contentKey="elbazarApp.adresse.indication">Indication</Translate>
                    </span>
                  </dt>
                  <dd>{adresseEntity.indication}</dd>
                  <dt>
                    <Translate contentKey="elbazarApp.adresse.zone">Zone</Translate>
                  </dt>
                  <dd>{adresseEntity.zoneNom ? adresseEntity.zoneNom : ''}</dd>
                  <dt>
                    <Translate contentKey="elbazarApp.adresse.codePostal">Code Postal</Translate>
                  </dt>
                  <dd>{adresseEntity.codePostalCodePostal ? adresseEntity.codePostalCodePostal : ''}</dd>
                </Col>
              </Row>
            </AvGroup>
          </AvForm>
          <Button tag={Link} to="/adresse" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/adresse/${adresseEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ adresse }: IRootState) => ({
  adresseEntity: adresse.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AdresseDetail);
