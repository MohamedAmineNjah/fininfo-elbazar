import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGestionFidelite } from 'app/shared/model/gestion-fidelite.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './gestion-fidelite.reducer';

export interface IGestionFideliteDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GestionFideliteDeleteDialog = (props: IGestionFideliteDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/gestion-fidelite');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.gestionFideliteEntity.id);
  };

  const { gestionFideliteEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="elbazarApp.gestionFidelite.delete.question">
        <Translate contentKey="elbazarApp.gestionFidelite.delete.question" interpolate={{ id: gestionFideliteEntity.id }}>
          Are you sure you want to delete this GestionFidelite?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-gestionFidelite" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ gestionFidelite }: IRootState) => ({
  gestionFideliteEntity: gestionFidelite.entity,
  updateSuccess: gestionFidelite.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GestionFideliteDeleteDialog);
