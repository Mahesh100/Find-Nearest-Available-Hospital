import { createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2', // Blue
    },
    secondary: {
      main: '#ff9800', // Orange
    },
    background: {
      default: '#f4f6fa',
      paper: '#fff',
    },
  },
  typography: {
    h3: {
      fontWeight: 700,
      color: '#1976d2',
    },
    h6: {
      color: '#333',
    },
  },
});

export default theme; 