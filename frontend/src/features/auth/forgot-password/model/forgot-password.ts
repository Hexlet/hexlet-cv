import axios from 'axios';

export async function forgotPassword(email: string): Promise<void> {
  try {
    const response = await axios.post('/api/auth/forgot-password', { email });
    
    if (response.status !== 200) {
      throw new Error('Failed to send reset email');
    }
    
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      const message = error.response?.data?.message || 'Network error occurred';
      throw new Error(message);
    }
    throw error;
  }
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ForgotPasswordResponse {
  message: string;
  resetToken?: string;
}
