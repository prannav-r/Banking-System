# Banking System Frontend

A modern, responsive web frontend for the Java Banking System with comprehensive validation rules and user-friendly interface.

## Features

### 🔐 Authentication

- **Separate Login/Register Pages**: Clean separation of authentication flows
- **Real-time Validation**: Instant feedback on form inputs
- **Secure Session Management**: LocalStorage-based session handling

### ✅ Validation Rules

- **Username**: 3-30 characters, unique, alphanumeric + underscores only
- **Password**: 6+ characters, must contain letters and numbers
- **Full Name**: 3+ characters, letters and spaces only
- **Age**: 18+ years old (minimum banking age)
- **Contact Number**: Exactly 10 digits
- **City**: 2+ characters, letters and spaces only

### 💰 Banking Operations

- **Deposit**: Add money to account with validation
- **Transfer**: Send money between accounts
- **Balance Enquiry**: Real-time balance display
- **Account Details**: Complete account information
- **Change Password**: Secure password updates
- **Delete Account**: Account deletion with confirmation

### 🎨 Modern UI/UX

- **Responsive Design**: Works on all devices
- **Professional Styling**: Clean, modern banking interface
- **Interactive Elements**: Smooth animations and transitions
- **Real-time Feedback**: Instant validation and notifications
- **Accessibility**: Keyboard navigation and screen reader support

## File Structure

```
frontend/
├── index.html          # Main HTML file
├── styles.css          # CSS styling
└── script.js           # JavaScript functionality
```

## Validation Features

### Real-time Validation

- **Live Feedback**: Validation messages appear as you type
- **Visual Indicators**: Green/red borders for valid/invalid fields
- **Help Text**: Contextual help messages below each field

### Comprehensive Rules

1. **Username Validation**:

   - Minimum 3 characters
   - Maximum 30 characters
   - Only letters, numbers, and underscores
   - Must be unique (checked against database)

2. **Password Validation**:

   - Minimum 6 characters
   - Must contain at least one letter
   - Must contain at least one number
   - Visual strength indicator

3. **Name Validation**:

   - Minimum 3 characters
   - Only letters and spaces allowed
   - Proper capitalization handling

4. **Age Validation**:

   - Minimum 18 years (banking age requirement)
   - Maximum 120 years (reasonable limit)
   - Number format validation

5. **Contact Validation**:

   - Exactly 10 digits
   - Handles spaces and dashes automatically
   - Format: XXXXXXXXXX

6. **City Validation**:
   - Minimum 2 characters
   - Only letters and spaces allowed
   - Proper capitalization

## Usage

### Running the Frontend

1. **Open `frontend/index.html`** in any modern web browser
2. **No server required** for basic functionality
3. **For full API integration**, connect to Java backend

### Testing Validation

1. **Try invalid inputs** to see validation messages
2. **Test edge cases** like special characters, short passwords
3. **Verify real-time feedback** as you type

## Browser Compatibility

- ✅ Chrome 90+
- ✅ Firefox 88+
- ✅ Safari 14+
- ✅ Edge 90+
- ✅ Mobile browsers

## API Integration

The frontend is designed to work with REST APIs. To connect to the Java backend:

1. **Start the Java backend** on port 8080
2. **Update API endpoints** in `script.js`
3. **Enable CORS** in the Java backend
4. **Test the integration**

### API Endpoints Expected:

- `POST /api/login` - User authentication
- `POST /api/register` - User registration
- `POST /api/deposit` - Deposit money
- `POST /api/transfer` - Transfer money
- `POST /api/change-password` - Change password
- `POST /api/delete-account` - Delete account

## Development

### Customizing Validation

Edit the validation methods in `script.js`:

```javascript
validateUsername(username) {
    // Add your custom validation logic
}
```

### Styling Changes

Modify `styles.css` for visual customization:

```css
.form-group input:focus {
  border-color: #your-color;
}
```

### Adding Features

Extend the `BankingSystem` class in `script.js`:

```javascript
class BankingSystem {
  // Add new methods here
}
```

## Security Features

- **Input Sanitization**: All inputs are validated and sanitized
- **XSS Protection**: No direct HTML injection
- **CSRF Protection**: Token-based requests (when API connected)
- **Secure Storage**: Sensitive data handled properly

## Performance

- **Lightweight**: Minimal dependencies
- **Fast Loading**: Optimized CSS and JavaScript
- **Responsive**: Smooth animations and transitions
- **Efficient**: Minimal DOM manipulation

---

**Note**: This frontend is designed to work with the Java Banking System backend. For full functionality, ensure the backend is running and properly configured.
