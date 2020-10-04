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

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './commande-lignes.reducer';
import { ICommandeLignes } from 'app/shared/model/commande-lignes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface ICommandeLignesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CommandeLignes = (props: ICommandeLignesProps) => {
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

  const { commandeLignesList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="commande-lignes-heading">
        <Translate contentKey="elbazarApp.commandeLignes.home.title">Commande Lignes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="elbazarApp.commandeLignes.home.createLabel">Create new Commande Lignes</Translate>
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
                  placeholder={translate('elbazarApp.commandeLignes.home.search')}
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
        {commandeLignesList && commandeLignesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quantite')}>
                  <Translate contentKey="elbazarApp.commandeLignes.quantite">Quantite</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prixHT')}>
                  <Translate contentKey="elbazarApp.commandeLignes.prixHT">Prix HT</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('remise')}>
                  <Translate contentKey="elbazarApp.commandeLignes.remise">Remise</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tva')}>
                  <Translate contentKey="elbazarApp.commandeLignes.tva">Tva</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('prixTTC')}>
                  <Translate contentKey="elbazarApp.commandeLignes.prixTTC">Prix TTC</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creeLe')}>
                  <Translate contentKey="elbazarApp.commandeLignes.creeLe">Cree Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('creePar')}>
                  <Translate contentKey="elbazarApp.commandeLignes.creePar">Cree Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifieLe')}>
                  <Translate contentKey="elbazarApp.commandeLignes.modifieLe">Modifie Le</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('modifiePar')}>
                  <Translate contentKey="elbazarApp.commandeLignes.modifiePar">Modifie Par</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="elbazarApp.commandeLignes.refCommande">Ref Commande</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="elbazarApp.commandeLignes.refProduit">Ref Produit</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commandeLignesList.map((commandeLignes, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${commandeLignes.id}`} color="link" size="sm">
                      {commandeLignes.id}
                    </Button>
                  </td>
                  <td>{commandeLignes.quantite}</td>
                  <td>{commandeLignes.prixHT}</td>
                  <td>{commandeLignes.remise}</td>
                  <td>{commandeLignes.tva}</td>
                  <td>{commandeLignes.prixTTC}</td>
                  <td>
                    {commandeLignes.creeLe ? <TextFormat type="date" value={commandeLignes.creeLe} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{commandeLignes.creePar}</td>
                  <td>
                    {commandeLignes.modifieLe ? (
                      <TextFormat type="date" value={commandeLignes.modifieLe} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{commandeLignes.modifiePar}</td>
                  <td>
                    {commandeLignes.refCommandeReference ? (
                      <Link to={`commande/${commandeLignes.refCommandeId}`}>{commandeLignes.refCommandeReference}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {commandeLignes.refProduitReference ? (
                      <Link to={`produit/${commandeLignes.refProduitId}`}>{commandeLignes.refProduitReference}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${commandeLignes.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${commandeLignes.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${commandeLignes.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="elbazarApp.commandeLignes.home.notFound">No Commande Lignes found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={commandeLignesList && commandeLignesList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ commandeLignes }: IRootState) => ({
  commandeLignesList: commandeLignes.entities,
  loading: commandeLignes.loading,
  totalItems: commandeLignes.totalItems,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CommandeLignes);
