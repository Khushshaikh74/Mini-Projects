import os
import tkinter as tk
from tkinter import filedialog, messagebox, simpledialog
from Crypto.Cipher import AES
from Crypto.Protocol.KDF import PBKDF2
import numpy as np
import cv2
from PIL import Image, ImageTk
import struct

# Constants
SALT = b'security_salt'
KEY_LENGTH = 32  # 256-bit AES key
IV_LENGTH = 16  # AES block size
HEADER_FORMAT = "III"  # Format for storing image dimensions (height, width, channels)

def derive_key(password):
    return PBKDF2(password.encode(), SALT, dkLen=KEY_LENGTH)

def encrypt_image(image_path, password):
    key = derive_key(password)
    iv = os.urandom(IV_LENGTH)
    
    image = cv2.imread(image_path)
    if image is None:
        messagebox.showerror("Error", "Failed to read the image!")
        return
    
    height, width, channels = image.shape
    image_bytes = image.tobytes()
    cipher = AES.new(key, AES.MODE_CBC, iv)
    
    # Padding to match AES block size
    pad_length = 16 - (len(image_bytes) % 16)
    image_bytes += bytes([pad_length]) * pad_length
    
    encrypted_bytes = cipher.encrypt(image_bytes)
    encrypted_data = iv + struct.pack(HEADER_FORMAT, height, width, channels) + encrypted_bytes
    
    save_path = filedialog.asksaveasfilename(defaultextension=".enc", filetypes=[("Encrypted Files", "*.enc")])
    if not save_path:
        return
    
    with open(save_path, "wb") as file:
        file.write(encrypted_data)
    
    # Display encrypted image representation
    new_window = tk.Toplevel()
    new_window.title("Encrypted Image Output")
    new_window.geometry("400x400")
    
    img = np.zeros((300, 300, 3), dtype=np.uint8)  # Placeholder for encrypted representation
    encrypted_placeholder = Image.fromarray(img)
    encrypted_placeholder = ImageTk.PhotoImage(encrypted_placeholder)
    img_label = tk.Label(new_window, image=encrypted_placeholder)
    img_label.image = encrypted_placeholder
    img_label.pack()
    
    messagebox.showinfo("Success", f"Image encrypted and saved to {save_path}")

def decrypt_image(encrypted_path, password):
    key = derive_key(password)
    
    with open(encrypted_path, "rb") as file:
        encrypted_data = file.read()
    
    iv = encrypted_data[:IV_LENGTH]
    header = encrypted_data[IV_LENGTH:IV_LENGTH + struct.calcsize(HEADER_FORMAT)]
    height, width, channels = struct.unpack(HEADER_FORMAT, header)
    encrypted_bytes = encrypted_data[IV_LENGTH + struct.calcsize(HEADER_FORMAT):]
    
    cipher = AES.new(key, AES.MODE_CBC, iv)
    decrypted_bytes = cipher.decrypt(encrypted_bytes)
    
    # Remove padding
    pad_length = decrypted_bytes[-1]
    if pad_length < 1 or pad_length > 16:
        messagebox.showerror("Error", "Incorrect password or corrupted file!")
        return
    decrypted_bytes = decrypted_bytes[:-pad_length]
    
    # Restore image shape
    original_image = np.frombuffer(decrypted_bytes, dtype=np.uint8).reshape((height, width, channels))
    
    save_path = filedialog.asksaveasfilename(defaultextension=".png", filetypes=[("PNG Image", "*.png")])
    if not save_path:
        return
    
    cv2.imwrite(save_path, original_image)
    
    # Display decrypted image in a new window
    new_window = tk.Toplevel()
    new_window.title("Decrypted Image Output")
    new_window.geometry("400x400")
    
    img = Image.open(save_path)
    img = img.resize((300, 300), Image.LANCZOS)
    img = ImageTk.PhotoImage(img)
    img_label = tk.Label(new_window, image=img)
    img_label.image = img
    img_label.pack()
    
    messagebox.showinfo("Success", f"Decrypted image saved to {save_path}")

def choose_file(action):
    file_path = filedialog.askopenfilename()
    if not file_path:
        return
    
    password = simpledialog.askstring("Password", "Enter seed key:", show='*')
    if not password:
        messagebox.showwarning("Warning", "Seed key is required!")
        return
    
    if action == "encrypt":
        encrypt_image(file_path, password)
    else:
        decrypt_image(file_path, password)

def create_gui():
    window = tk.Tk()
    window.title("Secure Image Encryption")
    window.geometry("600x500")
    window.configure(bg="#f0f0f0")
    
    tk.Label(window, text="🔒 Secure Image Encryption 🔐", font=("Arial", 22, "bold"), fg="darkblue", bg="#f0f0f0").pack(pady=20)
    
    tk.Button(window, text="🔐 Encrypt Image", command=lambda: choose_file("encrypt"), font=("Arial", 14), bg="green", fg="white", padx=10, pady=5).pack(pady=10)
    tk.Button(window, text="🔓 Decrypt Image", command=lambda: choose_file("decrypt"), font=("Arial", 14), bg="blue", fg="white", padx=10, pady=5).pack(pady=10)
    tk.Button(window, text="❌ Exit", command=window.quit, font=("Arial", 14), bg="red", fg="white", padx=10, pady=5).pack(pady=20)
    
    window.mainloop()

if __name__ == "__main__":
    create_gui()