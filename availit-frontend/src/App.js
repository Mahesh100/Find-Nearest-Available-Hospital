import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Container, Paper, Box } from '@mui/material';
import HospitalList from './components/HospitalList';
import HospitalForm from './components/HospitalForm';

function App() {
  const [editing, setEditing] = useState(null);
  const [refresh, setRefresh] = useState(false);

  return (
    <>
      <AppBar position="static" color="primary" sx={{ mb: 4 }}>
        <Toolbar>
          <Box component="img" src="/logo192.png" alt="AvailIt Logo" sx={{ height: 40, mr: 2 }} />
          <Typography variant="h5" color="inherit" sx={{ flexGrow: 1, fontWeight: 700 }}>
            AvailIt Hospital Availability
          </Typography>
        </Toolbar>
      </AppBar>
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <HospitalForm
            editing={editing}
            onSuccess={() => { setEditing(null); setRefresh(r => !r); }}
            onCancel={() => setEditing(null)}
          />
          <HospitalList
            onEdit={setEditing}
            refresh={refresh}
          />
        </Paper>
      </Container>
    </>
  );
}

export default App;
