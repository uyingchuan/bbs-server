package org.levons.bbs.common.exception

/**
 * 业务异常
 * 用于抛出所有跟业务相关的错误
 * 错误信息会返回前台用户
 */
class ServiceException(override val message: String?) : Exception(message)
