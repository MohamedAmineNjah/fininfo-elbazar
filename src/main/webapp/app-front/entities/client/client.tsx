import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import {
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from '../../shared/reducers';
import { getSearchEntities, getEntities } from './client.reducer';
import { IClient } from '../../shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from '../../config/constants';
import { ITEMS_PER_PAGE } from '../../shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from '../../shared/util/entity-utils';

export interface IClientProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Client = (props: IClientProps) => {
  const [search, setSearch] = useState('');
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    if (search) {
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    } else {
      props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
    }
  };

  const startSearching = () => {
    if (search) {
      setPaginationState({
        ...paginationState,
        activePage: 1,
      });
      props.getSearchEntities(
        search,
        paginationState.activePage - 1,
        paginationState.itemsPerPage,
        `${paginationState.sort},${paginationState.order}`
      );
    }
  };

  const clear = () => {
    setSearch('');
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort, search]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { clientList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="client-heading">
        <Translate contentKey="elbazarApp.client.home.title">Clients</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="elbazarApp.client.home.createLabel">Create new Client</Translate>
        </Link>
      </h2>
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
                  placeholder={translate('elbazarApp.client.home.search')}
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
        {clientList && clientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('civilite')}>
                  <Translate contentKey="elbazarApp.client.civilite">Civilite</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prenom')}>
                  <Translate contentKey="elbazarApp.client.prenom">Prenom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="elbazarApp.client.nom">Nom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateDeNaissance')}>
                  <Translate contentKey="elbazarApp.client.dateDeNaissance">Date De Naissance</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('email')}>
                  <Translate contentKey="elbazarApp.client.email">Email</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('mobile')}>
                  <Translate contentKey="elbazarApp.client.mobile">Mobile</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reglement')}>
                  <Translate contentKey="elbazarApp.client.reglement">Reglement</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('etat')}>
                  <Translate contentKey="elbazarApp.client.etat">Etat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('inscription')}>
                  <Translate contentKey="elbazarApp.client.inscription">Inscription</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('derniereVisite')}>
                  <Translate contentKey="elbazarApp.client.derniereVisite">Derniere Visite</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalAchat')}>
                  <Translate contentKey="elbazarApp.client.totalAchat">Total Achat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('profile')}>
                  <Translate contentKey="elbazarApp.client.profile">Profile</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pointsFidelite')}>
                  <Translate contentKey="elbazarApp.client.pointsFidelite">Points Fidelite</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creeLe')}>
                  <Translate contentKey="elbazarApp.client.creeLe">Cree Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creePar')}>
                  <Translate contentKey="elbazarApp.client.creePar">Cree Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifieLe')}>
                  <Translate contentKey="elbazarApp.client.modifieLe">Modifie Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiePar')}>
                  <Translate contentKey="elbazarApp.client.modifiePar">Modifie Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clientList.map((client, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${client.id}`} color="link" size="sm">
                      {client.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`elbazarApp.Civilite.${client.civilite}`} />
                  </td>
                  <td>{client.prenom}</td>
                  <td>{client.nom}</td>
                  <td>
                    {client.dateDeNaissance ? (
                      <TextFormat type="date" value={client.dateDeNaissance} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{client.email}</td>
                  <td>{client.mobile}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.RegMod.${client.reglement}`} />
                  </td>
                  <td>{client.etat ? 'true' : 'false'}</td>
                  <td>
                    {client.inscription ? <TextFormat type="date" value={client.inscription} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {client.derniereVisite ? <TextFormat type="date" value={client.derniereVisite} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{client.totalAchat}</td>
                  <td>
                    <Translate contentKey={`elbazarApp.ProfileClient.${client.profile}`} />
                  </td>
                  <td>{client.pointsFidelite}</td>
                  <td>{client.creeLe ? <TextFormat type="date" value={client.creeLe} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{client.creePar}</td>
                  <td>{client.modifieLe ? <TextFormat type="date" value={client.modifieLe} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{client.modifiePar}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${client.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${client.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${client.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
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
              <Translate contentKey="elbazarApp.client.home.notFound">No Clients found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={clientList && clientList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientList: client.entities,
  loading: client.loading,
  totalItems: client.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Client);
