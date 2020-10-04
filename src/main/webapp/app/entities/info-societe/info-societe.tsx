import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './info-societe.reducer';
import { IInfoSociete } from 'app/shared/model/info-societe.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInfoSocieteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const InfoSociete = (props: IInfoSocieteProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { infoSocieteList, match, loading } = props;
  return (
    <div>
      <h2 id="info-societe-heading">Informations de la société</h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.infoSociete.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {infoSocieteList && infoSocieteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.nomSociete">Nom Societe</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.tel1">Tel 1</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.tel2">Tel 2</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.tel3">Tel 3</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.email1">Email 1</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.email2">Email 2</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.facebook">Facebook</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.youtube">Youtube</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.instagram">Instagram</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.twitter">Twitter</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.tiktok">Tiktok</Translate>
                </th>
                <th>
                  <Translate contentKey="elbazarApp.infoSociete.matriculeFiscal">Matricule Fiscal</Translate>
                </th>
                <th>Valeur min panier</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {infoSocieteList.map((infoSociete, i) => (
                <tr key={`entity-${i}`}>
                  <td>{infoSociete.id}</td>
                  <td>{infoSociete.nomSociete}</td>
                  <td>{infoSociete.adresse}</td>
                  <td>{infoSociete.tel1}</td>
                  <td>{infoSociete.tel2}</td>
                  <td>{infoSociete.tel3}</td>
                  <td>{infoSociete.email1}</td>
                  <td>{infoSociete.email2}</td>
                  <td>{infoSociete.facebook}</td>
                  <td>{infoSociete.youtube}</td>
                  <td>{infoSociete.instagram}</td>
                  <td>{infoSociete.twitter}</td>
                  <td>{infoSociete.tiktok}</td>
                  <td>{infoSociete.matriculeFiscal}</td>
                  <td>{infoSociete.valeurMinPanier}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${infoSociete.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="elbazarApp.infoSociete.home.notFound">No Info Societes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ infoSociete }: IRootState) => ({
  infoSocieteList: infoSociete.entities,
  loading: infoSociete.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InfoSociete);
