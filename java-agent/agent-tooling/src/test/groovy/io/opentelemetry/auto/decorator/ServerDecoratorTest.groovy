package io.opentelemetry.auto.decorator


import io.opentelemetry.auto.api.MoreTags
import io.opentelemetry.auto.instrumentation.api.AgentSpan
import io.opentelemetry.auto.instrumentation.api.Tags

class ServerDecoratorTest extends BaseDecoratorTest {

  def span = Mock(AgentSpan)

  def "test afterStart"() {
    def decorator = newDecorator()
    when:
    decorator.afterStart(span)

    then:
    1 * span.setTag(Tags.COMPONENT, "test-component")
    1 * span.setTag(Tags.SPAN_KIND, "server")
    1 * span.setTag(MoreTags.SPAN_TYPE, decorator.spanType())
    0 * _
  }

  def "test beforeFinish"() {
    when:
    newDecorator().beforeFinish(span)

    then:
    0 * _
  }

  @Override
  def newDecorator() {
    return new ServerDecorator() {
      @Override
      protected String[] instrumentationNames() {
        return ["test1", "test2"]
      }

      @Override
      protected String spanType() {
        return "test-type"
      }

      @Override
      protected String component() {
        return "test-component"
      }
    }
  }
}