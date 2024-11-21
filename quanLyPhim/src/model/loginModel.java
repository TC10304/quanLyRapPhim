package model;

public class loginModel {
	private String taiKhoan;
	private String matKhau;
	
	public loginModel(String taiKhoan, String matKhau) {
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
	}
	
	public loginModel() {
	}

	public String getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	
	
	
}
