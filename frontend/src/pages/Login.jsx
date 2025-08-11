import React, { useState } from 'react';
import api from '../api';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { loginLocal } = useAuth();
  const nav = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post('/auth/login', { email, password });
      loginLocal(res.data);
      if (res.data.role === 'ROLE_ADMIN') nav('/admin');
      else nav('/employee');
    } catch (err) {
      alert(err?.response?.data || 'Login failed');
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ width: '100%', maxWidth: '400px' }}>
        <h3 className="text-center mb-4 text-primary">Login</h3>
        <form onSubmit={submit}>
          <input className="form-control mb-3" value={email} onChange={e => setEmail(e.target.value)} placeholder="Email" required />
          <input type="password" className="form-control mb-3" value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" required />
          <button className="btn btn-primary w-100"><i className="fas fa-sign-in-alt"></i> Login</button>
        </form>
      </div>
    </div>
  );
}
