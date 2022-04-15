package br.com.bioconnect.BioAcesso.model.dtodevice;

import java.math.BigInteger;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InsertPhotoReturnDTO {

	private BigInteger user_id;
	//private ScoreDTODevice scores;
	//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	//@JsonPropertyOrder({"center_pose_quality", "bounds_width", "vertical_center_offset","horizontal_center_offset", "sharpness_quality"})
	//Map<String, Object> scores;
	private Boolean success;
	//private List<ErrorReturnDTODevice> erros;
	//@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	//@JsonPropertyOrder({"code", "Face exists"})
	//Map<String, Object> errors;

	public BigInteger getUser_id() {
		return user_id;
	}

	public void setUser_id(BigInteger user_id) {
		this.user_id = user_id;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	static class ScoreDTODevice {
		private Integer bounds_width;
		private Integer horizontal_center_offset;
		private Integer vertical_center_offset;
		private Integer center_pose_quality;
		private Integer sharpness_quality;

		public Integer getBounds_width() {
			return bounds_width;
		}

		public void setBounds_width(Integer bounds_width) {
			this.bounds_width = bounds_width;
		}

		public Integer getHorizontal_center_offset() {
			return horizontal_center_offset;
		}

		public void setHorizontal_center_offset(Integer horizontal_center_offset) {
			this.horizontal_center_offset = horizontal_center_offset;
		}

		public Integer getVertical_center_offset() {
			return vertical_center_offset;
		}

		public void setVertical_center_offset(Integer vertical_center_offset) {
			this.vertical_center_offset = vertical_center_offset;
		}

		public Integer getCenter_pose_quality() {
			return center_pose_quality;
		}

		public void setCenter_pose_quality(Integer center_pose_quality) {
			this.center_pose_quality = center_pose_quality;
		}

		public Integer getSharpness_quality() {
			return sharpness_quality;
		}

		public void setSharpness_quality(Integer sharpness_quality) {
			this.sharpness_quality = sharpness_quality;
		}

	}

	public static class ErrorReturnDTODevice {
		private Integer code;
		private String message;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}
/*
 * "user_id":1212, "scores":{ "bounds_width":67, "horizontal_center_offset":-12,
 * "vertical_center_offset":-54, "center_pose_quality":862,
 * "sharpness_quality":601 }, "success":false, "errors":[ { "code":3,
 * "message":"Face exists" } ]
 * 
 */