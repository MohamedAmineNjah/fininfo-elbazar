import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
      width: '100%',
      '& > * + *': {
        marginTop: theme.spacing(2),
      },
    },
    heading: {
      fontSize: theme.typography.pxToRem(15),
      fontWeight: theme.typography.fontWeightRegular,
    },
    image: {
      width: '36%',
    },
    paper: {
      textAlign: 'center',
      color: theme.palette.text.secondary,
    },
    textColor: {
      color: '#E91F2B',
    },
    table: {
      tableLayout: 'fixed',
      width: '100%',
      borderSpacing: '10px',
      borderCollapse: 'separate',
    },
  })
);
